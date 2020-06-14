package com.example.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.lang.Result;
import com.example.entity.TbPost;
import com.example.entity.TbUser;
import com.example.entity.TbUserMessage;
import com.example.shiro.AccountProfile;
import com.example.util.UploadUtil;
import com.example.vo.UserMessageVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Nigori
 * @date 2020/6/10
 **/
@Controller
public class UserController extends BaseController {

    @Autowired
    UploadUtil uploadUtil;

    @GetMapping("/user/home")
    public String home() {
        TbUser user = userService.getById(getProfileId());

        List<TbPost> posts = postService.list(new QueryWrapper<TbPost>()
                .eq("user_id",getProfileId())
                //30天记录
                //.gt("created", DateUtil.offsetDay(new Date(),-30))
                .orderByDesc("created")
        );
        request.setAttribute("user",user);
        request.setAttribute("posts",posts);
        return "/user/home";
    }

    @GetMapping("/user/set")
    public String set() {
        TbUser user = userService.getById(getProfileId());
        request.setAttribute("user", user);

        return "/user/set";
    }

    @ResponseBody
    @PostMapping("/user/set")
    public Result doSet(TbUser user) {

        if(StrUtil.isNotBlank(user.getAvatar())) {

            TbUser temp = userService.getById(getProfileId());
            temp.setAvatar(user.getAvatar());
            userService.updateById(temp);

            AccountProfile profile = getProfile();
            profile.setAvatar(user.getAvatar());

            SecurityUtils.getSubject().getSession().setAttribute("profile", profile);

            return Result.success().action("/user/set#avatar");
        }

        if(StrUtil.isBlank(user.getUsername())) {
            return Result.fail("昵称不能为空");
        }
        int count = userService.count(new QueryWrapper<TbUser>()
                .eq("username", getProfile().getUsername())
                .ne("id", getProfileId()));
        if(count > 0) {
            return Result.fail("改昵称已被占用");
        }

        TbUser temp = userService.getById(getProfileId());
        temp.setUsername(user.getUsername());
        temp.setGender(user.getGender());
        temp.setSign(user.getSign());
        userService.updateById(temp);

        AccountProfile profile = getProfile();
        profile.setUsername(temp.getUsername());
        profile.setSign(temp.getSign());
        SecurityUtils.getSubject().getSession().setAttribute("profile", profile);

        return Result.success().action("/user/set#info");
    }

    @ResponseBody
    @PostMapping("/user/upload")
    public Result uploadAvatar(@RequestParam(value = "file") MultipartFile file) throws IOException {
        return uploadUtil.upload(UploadUtil.type_avatar, file);
    }

    /**
     * 修改密码
     * @param nowpass
     * @param pass
     * @param repass
     * @return
     */
    @ResponseBody
    @PostMapping("/user/repass")
    public Result repass(String nowpass, String pass, String repass) {
        if(!pass.equals(repass)) {
            return Result.fail("两次密码不相同");
        }

        TbUser user = userService.getById(getProfileId());

        String nowPassMd5 = SecureUtil.md5(nowpass);
        if(!nowPassMd5.equals(user.getPassword())) {
            return Result.fail("密码不正确");
        }

        user.setPassword(SecureUtil.md5(pass));
        userService.updateById(user);

        return Result.success().action("/user/set#pass");
    }

    @GetMapping("/user/index")
    public String index() {
        return "/user/index";
    }


    @ResponseBody
    @GetMapping("/user/public")
    public Result userPublic() {
        IPage page = postService.page(getPage(), new QueryWrapper<TbPost>()
                .eq("user_id", getProfileId())
                .orderByDesc("created"));

        return Result.success(page);
    }

    @ResponseBody
    @GetMapping("/user/collection")
    public Result collection() {
        IPage page = postService.page(getPage(), new QueryWrapper<TbPost>()
                .inSql("id", "SELECT post_id FROM tb_user_collection where user_id = " + getProfileId())
        );
        return Result.success(page);
    }

    @GetMapping("/user/message")
    public String message() {
        IPage<UserMessageVo> page = messageService.paging(getPage(), new QueryWrapper<TbUserMessage>()
                .eq("to_user_id", getProfileId())
                .orderByDesc("created")
        );

        // 把消息改成已读状态
        List<Long> ids = new ArrayList<>();
        System.out.println("1223dfghkdnsdjkfbhjngkdjnhvk");
        for(UserMessageVo messageVo : page.getRecords()) {
            if(messageVo.getStatus() == 0) {
                System.out.println("d7978978fghkdnsdjkfbhjngkdjnhvk");
                ids.add(messageVo.getId());
            }
        }
        System.out.println("dfghkdnsdjkfbds548793413343hjngkdjnhvk");
        // 批量修改成已读
        messageService.updateToReaded(ids);
        System.out.println("dfghkdnsdjkfbhjngkdjnhvk");
        request.setAttribute("pageData", page);
        return "/user/message";
    }

    @ResponseBody
    @PostMapping("/message/remove/")
    public Result msgRemove(Long id,
                            @RequestParam(defaultValue = "false") Boolean all) {

        boolean remove = messageService.remove(new QueryWrapper<TbUserMessage>()
                .eq("to_user_id", getProfileId())
                .eq(!all, "id", id));

        return remove ? Result.success(null) : Result.fail("删除失败");
    }

    @ResponseBody
    @RequestMapping("/message/nums/")
    public Map msgNums() {

        int count = messageService.count(new QueryWrapper<TbUserMessage>()
                .eq("to_user_id", getProfileId())
                .eq("status", "0")
        );
        return MapUtil.builder("status", 0)
                .put("count", count).build();
    }
}

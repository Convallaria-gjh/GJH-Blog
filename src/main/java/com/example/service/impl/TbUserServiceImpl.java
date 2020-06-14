package com.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.lang.Result;
import com.example.entity.TbUser;
import com.example.mapper.TbUserMapper;
import com.example.service.TbUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shiro.AccountProfile;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Nigori
 * @since 2020-06-03
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserService {

    @Override
    public Result register(TbUser user) {

        int count = this.count(new QueryWrapper<TbUser>()
                .eq("email", user.getEmail())
                .or()
                .eq("username", user.getUsername())
        );
        if(count > 0) return Result.fail("用户名或邮箱已被占用");

        TbUser temp = new TbUser();
        temp.setUsername(user.getUsername());
        temp.setPassword(SecureUtil.md5(user.getPassword()));       //密码加密
        temp.setEmail(user.getEmail());
        temp.setAvatar("/res/images/avatar/default.png");

        temp.setCreated(new Date());
        temp.setPoint(0);
        temp.setVipLevel(0);
        temp.setCommentCount(0);
        temp.setPostCount(0);
        temp.setGender("0");
        this.save(temp);

        return Result.success();
    }

    @Override
    public AccountProfile login(String email, String password) {

        TbUser user = this.getOne(new QueryWrapper<TbUser>().eq("email", email));
        if(user == null) {
            throw new UnknownAccountException();
        }
        if(!user.getPassword().equals(password)){
            throw new IncorrectCredentialsException();
        }

        user.setLasted(new Date());
        this.updateById(user);

        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user, profile);

        return profile;
    }
}

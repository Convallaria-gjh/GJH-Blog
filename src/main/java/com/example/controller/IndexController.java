package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Nigori
 * @date 2020/6/2
 **/
@Controller
public class IndexController extends BaseController {

    @RequestMapping({"","/","index"})
    public String index() {

        IPage result = postService.paging(getPage(),null,null,null,null,"created");

        request.setAttribute("pageData",result);
        request.setAttribute("currentCategoryId",0);
        return "index";
    }

    @RequestMapping("/search")
    public String search(String q) {

        IPage pageData = searchService.search(getPage(), q);

        request.setAttribute("q",q);
        request.setAttribute("pageData",pageData);
        return "search";
    }
}

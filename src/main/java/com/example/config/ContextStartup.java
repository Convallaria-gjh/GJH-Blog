package com.example.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.TbCategory;
import com.example.service.TbCategoryService;
import com.example.service.TbPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;


/**
 * @author Nigori
 * @date 2020/6/3
 **/
@Component
public class ContextStartup implements ApplicationRunner, ServletContextAware {

    @Autowired
    TbCategoryService categoryService;
    ServletContext servletContext;

    @Autowired
    TbPostService postService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<TbCategory> categories = categoryService.list(new QueryWrapper<TbCategory>()
                .eq("status", 0)
        );
        servletContext.setAttribute("categorys",categories);

        postService.initWeekRank();
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}

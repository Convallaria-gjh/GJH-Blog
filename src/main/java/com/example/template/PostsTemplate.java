package com.example.template;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.templates.DirectiveHandler;

import com.example.common.templates.TemplateDirective;
import com.example.service.TbPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Nigori
 * @date 2020/6/4
 **/
@Component
public class PostsTemplate extends TemplateDirective {

    @Autowired
    TbPostService postService;

    @Override
    public String getName() {
        return "posts";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer level = handler.getInteger("level");
        Integer pagenum = handler.getInteger("pagenum",1);
        Integer size = handler.getInteger("size",2);
        Long categoryId = handler.getLong("categoryId");

        IPage page = postService.paging(new Page(pagenum,size),categoryId,null,level,null,"created");

        handler.put(RESULTS,page).render();
    }
}

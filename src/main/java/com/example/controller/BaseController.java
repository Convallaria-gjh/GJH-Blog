package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.service.*;
import com.example.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Nigori
 * @date 2020/6/3
 **/
public class BaseController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    TbPostService postService;

    @Autowired
    TbCommentService commentService;

    @Autowired
    TbUserService userService;

    @Autowired
    TbUserMessageService messageService;

    @Autowired
    TbUserCollectionService collectionService;

    @Autowired
    TbCategoryService categoryService;

    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    WsService wsService;

    @Autowired
    SearchService searchService;

    @Autowired
    ChatService chatService;

    public Page getPage() {
        int pagenum = ServletRequestUtils.getIntParameter(request,"pagenum",1);
        int size = ServletRequestUtils.getIntParameter(request,"size",2);
        return new Page(pagenum, size);
    }

    protected AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getProfileId() {
        return getProfile().getId();
    }
}

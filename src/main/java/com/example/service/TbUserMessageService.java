package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.TbUserMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.UserMessageVo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Nigori
 * @since 2020-06-03
 */
public interface TbUserMessageService extends IService<TbUserMessage> {

    IPage<UserMessageVo> paging(Page page, QueryWrapper<TbUserMessage> wrapper);

    void updateToReaded(List<Long> ids);
}

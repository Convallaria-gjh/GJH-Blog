package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.TbUserMessage;
import com.example.mapper.TbUserMessageMapper;
import com.example.service.TbUserMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vo.UserMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Nigori
 * @since 2020-06-03
 */
@Service
public class TbUserMessageServiceImpl extends ServiceImpl<TbUserMessageMapper, TbUserMessage> implements TbUserMessageService {

    @Autowired
    TbUserMessageMapper messageMapper;

    @Override
    public IPage<UserMessageVo> paging(Page page, QueryWrapper<TbUserMessage> wrapper) {
        return messageMapper.selectMessages(page, wrapper);
    }

    @Override
    public void updateToReaded(List<Long> ids) {
        if(ids.isEmpty()) {
            System.out.println("23565676345");
            return;}

        messageMapper.updateToReaded(new QueryWrapper<TbUserMessage>()
                .in("id", ids)
        );

    }
}

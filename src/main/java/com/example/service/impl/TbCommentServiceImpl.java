package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.TbComment;
import com.example.mapper.TbCommentMapper;
import com.example.service.TbCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Nigori
 * @since 2020-06-03
 */
@Service
public class TbCommentServiceImpl extends ServiceImpl<TbCommentMapper, TbComment> implements TbCommentService {

    @Autowired
    TbCommentMapper commentMapper;

    @Override
    public IPage<CommentVo> paing(Page page, Long postId, Long userId, String order) {
        return commentMapper.selectComments(page,new QueryWrapper<TbComment>()
                .eq(postId != null,"post_id",postId)
                .eq(userId !=null,"user_id",userId)
                .orderByDesc(order != null,order)
        );
    }
}

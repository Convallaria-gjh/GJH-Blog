package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.TbComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.CommentVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Nigori
 * @since 2020-06-03
 */
public interface TbCommentService extends IService<TbComment> {

    IPage<CommentVo> paing(Page page, Long postId, Long userId, String order);
}

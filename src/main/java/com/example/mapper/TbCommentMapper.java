package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.TbComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vo.CommentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Nigori
 * @since 2020-06-03
 */
@Component
public interface TbCommentMapper extends BaseMapper<TbComment> {

    IPage<CommentVo> selectComments(Page page, @Param(Constants.WRAPPER) QueryWrapper<TbComment> wrapper);
}

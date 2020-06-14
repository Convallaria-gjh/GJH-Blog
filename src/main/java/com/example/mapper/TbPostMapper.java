package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.TbPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vo.PostVo;
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
public interface TbPostMapper extends BaseMapper<TbPost> {

    IPage<PostVo> selectPosts(Page page, @Param(Constants.WRAPPER) QueryWrapper<TbPost> wrapper);

    PostVo selectOnePost(@Param(Constants.WRAPPER) QueryWrapper<TbPost> wrapper);
}

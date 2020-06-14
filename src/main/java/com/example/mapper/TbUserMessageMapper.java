package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.TbUserMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vo.UserMessageVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Nigori
 * @since 2020-06-03
 */
@Component
public interface TbUserMessageMapper extends BaseMapper<TbUserMessage> {

    IPage<UserMessageVo> selectMessages(Page page, @Param(Constants.WRAPPER) QueryWrapper<TbUserMessage> wrapper);

    @Transactional
    @Update("update tb_user_message set status = 1 ${ew.customSqlSegment}")
    void updateToReaded(QueryWrapper<TbUserMessage> wrapper);
}

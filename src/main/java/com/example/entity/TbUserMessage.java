package com.example.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Nigori
 * @since 2020-06-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class TbUserMessage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 发送消息的用户ID
     */
    private Long fromUserId;

    /**
     * 接收消息的用户ID
     */
    private Long toUserId;

    /**
     * 消息可能关联的帖子
     */
    private Long postId;

    /**
     * 消息可能关联的评论
     */
    private Long commentId;

    private String content;

    /**
     * 消息类型 0系统消息 1评论文章 2评论评论
     */
    private Integer type;

    /**
     * 判断消息是否已读
     */
    private Integer status;


}

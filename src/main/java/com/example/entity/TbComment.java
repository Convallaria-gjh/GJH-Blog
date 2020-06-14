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
public class TbComment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 评论的内容
     */
    private String content;

    /**
     * 回复的评论ID
     */
    private Long parentId;

    /**
     * 评论的内容ID
     */
    private Long postId;

    /**
     * 评论的用户ID
     */
    private Long userId;

    /**
     * “顶”的数量
     */
    private Integer voteUp;

    /**
     * “踩”的数量
     */
    private Integer voteDown;

    /**
     * 置顶等级
     */
    private Integer level;


}

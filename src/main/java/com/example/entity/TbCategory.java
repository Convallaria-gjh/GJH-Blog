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
public class TbCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String name;

    /**
     * 内容描述
     */
    private String content;

    private String summary;

    /**
     * 图标
     */
    private String icon;

    /**
     * 该分类的内容数量
     */
    private Integer postCount;

    /**
     * 排序编码
     */
    private Integer orderNum;

    /**
     * 父级分类的ID
     */
    private Long parentId;

    /**
     * SEO关键字
     */
    private String metaKeywords;

    /**
     * SEO描述内容
     */
    private String metaDescription;


}

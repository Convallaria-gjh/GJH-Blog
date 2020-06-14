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
public class TbUserCollection extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long postId;

    private Long postUserId;


}

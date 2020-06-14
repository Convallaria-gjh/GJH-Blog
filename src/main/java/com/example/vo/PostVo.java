package com.example.vo;

import com.example.entity.TbPost;
import lombok.Data;

/**
 * @author Nigori
 * @date 2020/6/3
 **/
@Data
public class PostVo extends TbPost {

    private Long authorId;
    private String authorName;
    private String authorAvatar;
    private String categoryName;
}

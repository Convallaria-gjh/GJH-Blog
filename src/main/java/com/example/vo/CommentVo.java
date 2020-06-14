package com.example.vo;

import lombok.Data;

import java.awt.*;

/**
 * @author Nigori
 * @date 2020/6/7
 **/
@Data
public class CommentVo extends Component {

    private Long authorId;
    private String authorName;
    private String authorAvatar;
}

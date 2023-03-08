package com.srb.srb.domain.dto;

import com.srb.srb.domain.entity.Article;
import com.srb.srb.domain.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {

    private String title;

    private String content;

    private Article article;

    public Comment toEntity() {
        return Comment.builder()
                .title(title)
                .content(content)
                .article(article)
                .build();
    }
}

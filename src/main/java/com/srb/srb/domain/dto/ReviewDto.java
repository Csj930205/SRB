package com.srb.srb.domain.dto;

import com.srb.srb.domain.entity.Member;
import com.srb.srb.domain.entity.Review;
import com.srb.srb.domain.entity.Song;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDto {

    private String title;

    private String content;

    private Member member;

    private Song song;

    public Review toEntity() {
        return Review.builder()
                .title(title)
                .content(content)
                .member(member)
                .song(song)
                .build();
    }
}

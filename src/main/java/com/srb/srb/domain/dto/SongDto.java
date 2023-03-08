package com.srb.srb.domain.dto;

import com.srb.srb.domain.entity.Song;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SongDto {

    private String title;

    private String content;

    private int category;

    public Song toEntity() {
        return Song.builder()
                .title(title)
                .content(content)
                .category(category)
                .build();
    }
}

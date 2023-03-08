package com.srb.srb.domain.dto;

import com.srb.srb.domain.entity.Member;
import com.srb.srb.domain.entity.PersonalList;
import com.srb.srb.domain.entity.Song;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonalListDto {

    private Member member;

    private Song song;

    public PersonalList toEntity() {
        return PersonalList.builder()
                .member(member)
                .song(song)
                .build();
    }
}

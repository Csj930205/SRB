package com.srb.srb.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personalList")
@Getter
@NoArgsConstructor
public class PersonalList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personalList_seq")
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "member_seq")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "song_seq")
    private Song song;

    @Builder
    public PersonalList(Long seq, Member member, Song song) {
        this.seq = seq;
        this.member = member;
        this.song = song;
    }
}

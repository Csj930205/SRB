package com.srb.srb.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_seq")
    private Long seq;

    @Column(name = "review_title")
    private String title;

    @Column(name = "review_content")
    private String content;

    @Column(name = "review_del_yn", columnDefinition = "varchar(1) default 'N' ")
    private String delYn;

    @Column(name = "review_created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "review_modified_date")
    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "song_seq")
    private Song song;

    @Builder
    public Review(Long seq, String title, String content, String delYn, LocalDateTime createdDate,LocalDateTime modifiedDate,
                  Member member, Song song) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.delYn = delYn;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.member = member;
        this.song = song;
    }
}

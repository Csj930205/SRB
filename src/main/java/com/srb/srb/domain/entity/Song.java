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
@Table(name = "song")
@NoArgsConstructor
@Getter
@DynamicUpdate
@DynamicInsert
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_seq")
    private Long seq;

    @Column(name = "song_title", length = 50)
    private String title;

    @Column(name = "song_content")
    private String content;

    @Column(name = "song_category")
    private int category;

    @Column(name = "song_del_yn", columnDefinition = "varchar(1) default 'N' ")
    private String delYn;

    @Column(name = "song_created_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "song_modified_date", insertable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @Builder
    public Song(Long seq, String title, String content, int category, String delYn,
                LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.category = category;
        this.delYn = delYn;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

}

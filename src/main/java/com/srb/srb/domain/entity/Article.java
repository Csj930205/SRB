package com.srb.srb.domain.entity;

import com.srb.srb.domain.dto.ArticleDto;
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
@Table(name = "article")
@NoArgsConstructor
@Getter
@DynamicInsert
@DynamicUpdate
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_seq")
    private Long seq;

    @Column(name = "article_title")
    private String title;

    @Column(name = "article_content")
    private String content;

    @Column(name = "article_del_yn", columnDefinition = "varchar(1) default 'N' ")
    private String delYn;

    @Column(name = "article_created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "article_modified_date")
    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "song_seq")
    private Song song;

    @Builder
    public Article(Long seq, String title, String content, String delYn, LocalDateTime createdDate, LocalDateTime modifiedDate,
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

    /**
     * 게시글 수정
     * @param articleDto
     */
    public void articleModify(ArticleDto articleDto) {
        this.title = articleDto.getTitle();
        this.content = articleDto.getContent();
    }

    /**
     * 게시글 삭제(업데이트)
     */
    public void articleDelete() {
        this.delYn = "Y";
    }
}

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
@Table(name = "comment")
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_seq")
    private Long seq;

    @Column(name = "comment_title")
    private String title;

    @Column(name = "comment_content")
    private String content;

    @Column(name = "comment_del_yn", columnDefinition = "varchar(1) default 'N' ")
    private String delYn;

    @Column(name = "comment_created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "comment_modified_date")
    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "article_seq")
    private Article article;

    @Builder
    public Comment(Long seq, String title, String content, String delYn, LocalDateTime createdDate, LocalDateTime modifiedDate,
                   Article article) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.delYn = delYn;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.article = article;
    }

}

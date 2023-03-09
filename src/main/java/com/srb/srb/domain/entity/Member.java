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
import java.util.List;

@Entity
@Table(name = "member")
@NoArgsConstructor
@Getter
@DynamicInsert
@DynamicUpdate
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq")
    private Long seq;

    @Column(name = "member_id", nullable = false)
    private String id;

    @Column(name = "member_pw", nullable = false)
    private String pw;

    @Column(name = "member_name", nullable = false, length = 30)
    private String name;

    @Column(name = "member_phone", nullable = false, length = 30)
    private String phone;

    @Column(name = "member_email", nullable = false, length = 30)
    private String email;

    @Column(name = "member_del_yn", columnDefinition = "varchar(1) default 'N' ")
    private String delYn;

    @Column(name = "member_role")
    private String role;

    @Column(name = "member_fail_count", columnDefinition = "Integer default '0' ", insertable = false)
    private int failCount;

    @Column(name = "member_enabled", columnDefinition = "TINYINT default '1' ", insertable = false)
    private boolean enabled;

    @Column(name = "member_signup_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime signupDate;

    @Column(name = "member_modified_date", insertable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Article> articleList;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Review> reviews;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<LoginLog> loginLogs;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<PersonalList> personalLists;

    @Builder
    public Member(Long seq, String id, String pw, String name, String phone, String email,
                  String delYn, String role,int failCount, boolean enabled, LocalDateTime signupDate, LocalDateTime modifiedDate,
                  List<Article> articleList, List<Review> reviews, List<LoginLog> loginLogs, List<PersonalList> personalLists) {
        this.seq = seq;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.delYn = delYn;
        this.role = role;
        this.failCount = failCount;
        this.enabled = enabled;
        this.signupDate = signupDate;
        this.modifiedDate = modifiedDate;
        this.articleList = articleList;
        this.reviews = reviews;
        this.loginLogs = loginLogs;
        this.personalLists = personalLists;
    }


}

package com.srb.srb.domain.dto;

import com.srb.srb.domain.entity.LoginLog;
import com.srb.srb.domain.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginLogDto {

    private String access;

    private String ip;

    private Member member;

    public LoginLog toEntity () {
        return LoginLog.builder()
                .access(access)
                .ip(ip)
                .member(member)
                .build();
    }
}

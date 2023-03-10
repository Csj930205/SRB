package com.srb.srb.domain.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@RedisHash(value = "refreshToken")
@Getter
@Setter
@NoArgsConstructor
public class Token {

    @Id
    @Indexed
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String key;

    private String value;

    @TimeToLive
    private Long expireTime;

    @Builder
    public Token(String key, String value, Long expireTime) {
        this.key = key;
        this.value = value;
        this.expireTime = expireTime;
    }

}

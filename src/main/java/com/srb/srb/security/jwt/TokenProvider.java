package com.srb.srb.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.srb.srb.domain.entity.Member;
import com.srb.srb.domain.entity.Token;
import com.srb.srb.repository.TokenRepository;
import com.srb.srb.security.MemberDetails;
import com.srb.srb.service.MemberService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.util.Date;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private Long accessTokenTime = 60 * 30 * 1000L;

    private Long refreshTokenTime = 60 * 60 * 7L;

    private final TokenRepository tokenRepository;

    private final MemberService memberService;

    /**
     * 객체초기화 및 secretKey Base64 인코딩
     */
    @PostConstruct
    public void init() {
        secretKey = Base64Utils.encodeToString(secretKey.getBytes());
    }

    /**
     * JWT Token 생성
     * @param member
     * @param tokenValidTime
     * @return
     */
    public Token createToken(Member member, long tokenValidTime) {
        Claims claims = Jwts.claims().setSubject(member.getId());
        claims.put("role", member.getRole());
        Date now = new Date();

        String token =
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + tokenValidTime))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact();

        return Token.builder()
                .key(member.getId())
                .value(token)
                .expireTime(tokenValidTime)
                .build();
    }

    /**
     * AccessToken 생성
     * @param member
     * @return
     */
    public Token accessTokenCreate(Member member) {
        return createToken(member, accessTokenTime);
    }

    /**
     * RefreshToken 생성
     * @param member
     * @return
     */
    public Token refreshTokenCreate(Member member) {
        return createToken(member, refreshTokenTime);
    }

    /**
     * 토큰에서 인증정보 조회
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        HashMap<String, String> payload = getPayloadByToken(token);
        MemberDetails member = memberService.loadUserByUsername(payload.get("sub"));
        return new UsernamePasswordAuthenticationToken(member.getMember().getId(), "", member.getAuthorities());
    }

    /**
     * 토큰에서 회원 정보조회
     * @param token
     * @return
     */
    public HashMap<String, String> getPayloadByToken(String token) {
        try {
            String[] splitJwt = token.split("\\.");
            String payload = new String(Base64Utils.decode(splitJwt[1].getBytes()));
            return new ObjectMapper().readValue(payload, HashMap.class);
        } catch (JsonProcessingException e) {
            return null;
        } catch (IllegalArgumentException e) {
            throw new JwtException("토큰정보가 유효하지 않습니다.");
        } catch (NullPointerException e) {
            throw new JwtException("유효하지 않은 정보입니다.");
        }
    }

    /**
     * 요청정보 헤더에서 토큰 추출
     * @param request
     * @return
     */
    public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    /**
     * AccessToken 유효성 검사
     * @param token
     * @return
     */
    public boolean accessTokenValidationToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            throw new JwtException("Access-Token 기한이 만료되었습니다. 재발급 신청을 해주세요.");
        } catch (IllegalArgumentException e) {
            throw new JwtException("유효하지 않은 토큰입니다.");
        } catch (SignatureException e) {
            throw new JwtException("토큰값이 잘못되었습니다.");
        } catch (NullPointerException e) {
            throw new JwtException("유효하지 않은 요청입니다.");
        }
    }

    /**
     * RefreshToken 유효성 검사
     * @param token
     * @return
     */
    public boolean refreshTokenValidationToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            throw new JwtException("Refresh-Token 기한이 만료되었습니다. 재로그인을 해주세요.");
        } catch (IllegalArgumentException e) {
            throw new JwtException("유효하지 않은 토큰입니다.");
        } catch (SignatureException e) {
            throw new JwtException("토큰값이 잘못되었습니다.");
        } catch (NullPointerException e) {
            throw new JwtException("유효하지 않은 요청입니다.");
        }
    }

    /**
     * 응답헤더에 토큰 전송(추후 쿠키로 변환 예정)
     * @param response
     * @param token
     */
    public void setHeaderAccessToken(HttpServletResponse response, String token) {
        response.setHeader("Authorization", token);
    }

    /**
     * RefreshToken 조회
     * @param id
     * @return
     */
    public Token getRefreshToken(String id) {
        return tokenRepository.findByKey(id);
    }

    /**
     * RefreshToken 만료시 Redis에서 삭제
     * @param id
     */
    @Transactional
    public void deleteRefreshToken(String id) {
        tokenRepository.deleteById(id);
    }
}

package com.srb.srb.common;

import com.srb.srb.domain.dto.LoginLogDto;
import com.srb.srb.domain.dto.MemberDto;
import com.srb.srb.domain.entity.Token;
import com.srb.srb.repository.LoginLogRepository;
import com.srb.srb.repository.TokenRepository;
import com.srb.srb.security.MemberDetails;
import com.srb.srb.security.jwt.TokenProvider;
import com.srb.srb.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoginCommon {

    private final TokenProvider tokenProvider;
    private final MemberService memberService;

    private final LoginLogRepository loginLogRepository;

    private final PasswordEncoder passwordEncoder;


    private final TokenRepository tokenRepository;

    /**
     * 로그인 요청 후 성공 시 Token 발급
     * @param memberDto
     * @param request
     * @return
     */
    public Map<String, Object> loginIdPwCompare(MemberDto memberDto, HttpServletRequest request, HttpServletResponse response) {
        LoginLogDto loginLogDto = new LoginLogDto();
        Map<String, Object> result = new HashMap<>();
        String ip = getClientIp(request);
        MemberDetails memberId = memberService.loadUserByUsername(memberDto.getId());
        String getToken = tokenProvider.resolveAccessToken(request);
        Token getRefreshToken = tokenProvider.getRefreshToken(memberId.getUsername());

        if (memberId.isAccountNonLocked() == true) {
            if ( passwordEncoder.matches(memberDto.getPw(), memberId.getPassword()) ) {
                memberService.failCountClear(memberId.getUsername());
                loginLogDto.setAccess("성공");

                if (getToken == null) {
                    Token accessToken = tokenProvider.accessTokenCreate(memberId.getMember());
                    tokenProvider.setHeaderAccessToken(response, accessToken.getValue());
                }

                if (getRefreshToken == null) {
                    Token refreshToken = tokenProvider.refreshTokenCreate(memberId.getMember());
                    tokenRepository.save(refreshToken);
                }

                result.put("result", "success");
                result.put("code", HttpStatus.OK.value());
                result.put("message", "로그인 성공");
            } else {
                memberService.failCountUp(memberId.getUsername());
                loginLogDto.setAccess("실패");

                result.put("result", "fail");
                result.put("code", HttpStatus.BAD_REQUEST.value());
                result.put("message", "비밀번호가 일치하지 않습니다.");
            }
        } else {
            loginLogDto.setAccess("실패");

            result.put("result", "fail");
            result.put("code", HttpStatus.FORBIDDEN.value());
            result.put("failCount", memberId.getFailCount());
            result.put("enabled", memberId.isAccountNonLocked());
            result.put("message", "계정이 잠금되었습니다.");
        }
        loginLogDto.setMember(memberId.getMember());
        loginLogDto.setIp(ip);
        loginLogRepository.save(loginLogDto.toEntity());

        return result;
    }


    /**
     * 사용자 IP 추출
     * @param request
     * @return
     */
    public String getClientIp(HttpServletRequest request) {
        String clientIp = null;
        boolean isIpHeader = false;

        List<String> headerList = new ArrayList<>();
        headerList.add("X-Forwarded-For");
        headerList.add("HTTP_CLIENT_IP");
        headerList.add("HTTP_X_FORWARDED_FOR");
        headerList.add("HTTP_X_FORWARDED");
        headerList.add("HTTP_FORWARDED_FOR");
        headerList.add("HTTP_FORWARDED");
        headerList.add("WL-proxy-Client_IP");
        headerList.add("HTTP_VIA");
        headerList.add("IPV6_ADR");

        for (String header : headerList) {
            clientIp = request.getHeader(header);

            if (StringUtils.hasText(clientIp) && !clientIp.equals("unknown")) {
                isIpHeader = true;
                break;
            }
        }
        if (!isIpHeader) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }

}

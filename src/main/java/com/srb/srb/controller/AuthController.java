package com.srb.srb.controller;

import com.srb.srb.common.LoginCommon;
import com.srb.srb.domain.dto.MemberDto;
import com.srb.srb.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final MemberService memberService;

    private final LoginCommon loginCommon;

    /**
     * 로그인
     * @param memberDto
     * @return
     */
    @PostMapping("login")
    public ResponseEntity<Map<String, Object>> login (@RequestBody MemberDto memberDto, HttpServletRequest request) {
        Map<String, Object> result = loginCommon.loginIdPwCompare(memberDto, request);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 회원가입
     * @param memberDto
     * @return
     */
    @PostMapping("signup")
    public ResponseEntity<Map<String, Object>> signupMember(@RequestBody MemberDto memberDto)  {
        Map<String, Object> result = memberService.joinMember(memberDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 로그아웃
     * @return
     */
    @PostMapping("logout")
    public ResponseEntity<Map<String, Object>> logout () {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        result.put("code", HttpStatus.OK.value());
        result.put("message", "로그아웃 되었습니다.");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

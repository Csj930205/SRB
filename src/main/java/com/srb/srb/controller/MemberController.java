package com.srb.srb.controller;

import com.srb.srb.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;


    /**
     * 회원 전체 조회
     * @return
     */
    @GetMapping("allList")
    public ResponseEntity<Map<String, Object>> allList() {
        Map<String, Object> result = memberService.memberList();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 회원 상세 조회
     * @param seq
     * @return
     */
    @GetMapping("/detailMember/{seq}")
    public ResponseEntity<Map<String, Object>> detailMember(@PathVariable("seq") Long seq) {
        Map<String, Object> result = memberService.detailMember(seq);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}

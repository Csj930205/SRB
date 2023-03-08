package com.srb.srb.service;

import com.srb.srb.domain.dto.MemberDto;
import com.srb.srb.domain.entity.Member;
import com.srb.srb.repository.MemberRepository;
import com.srb.srb.security.MemberDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    /**
     * 회원 전체조회
     * @return
     */
    public Map<String, Object> memberList() {
        Map<String, Object> result = new HashMap<>();
        List<Member> memberList = memberRepository.findAll();

        result.put("result", "success");
        result.put("code", HttpStatus.OK.value());
        result.put("memberList", memberList);

        return result;
    }

    /**
     * 상세회원 조회
     * @param seq
     * @return
     */
    public Map<String, Object> detailMember(Long seq) {
        Map<String, Object> result = new HashMap<>();
        Optional<Member> detailMember = memberRepository.findById(seq);

        if (detailMember != null) {
            result.put("result", "success");
            result.put("code", HttpStatus.OK.value());
            result.put("detailMember", detailMember);
        } else {
            result.put("result", "fail");
            result.put("code", HttpStatus.NOT_FOUND.value());
            result.put("message", "존재하지 않는 회원입니다.");
        }
        return result;
    }

    /**
     * 회원 유무 검사
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public MemberDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberId = memberRepository.findById(username);

        if (memberId != null) {
            return new MemberDetails(memberId);
        } else {
            return null;
        }
    }

    /**
     * 회원기입
     * @param memberDto
     * @return
     */
    @Transactional
    public Map<String, Object> joinMember(MemberDto memberDto) {
        Map<String, Object> result = new HashMap<>();
        Member memberId = memberRepository.findById(memberDto.getId());
        boolean validationPassword = validationPassword(memberDto.getPw());

        if ( memberId == null) {
            if (validationPassword) {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                memberDto.setPw(bCryptPasswordEncoder.encode(memberDto.getPw()));
                memberRepository.save(memberDto.toEntity());
                result.put("result", "success");
                result.put("code", HttpStatus.OK.value());
                result.put("message", "회원가입이 성공하였습니다.");
            } else {
                result.put("result", "fail");
                result.put("code", HttpStatus.OK.value());
                result.put("message", "비밀번호 정규식과 일치하지 않습니다.");
            }
        } else {
            result.put("result", "fail");
            result.put("code", HttpStatus.BAD_REQUEST.value());
            result.put("result", "회원가입에 실패하였습니다.");
        }
        return result;
    }

    /**
     * 비밀번호 유효성 검사
     * @param password
     * @return
     */
    public boolean validationPassword (String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 로그인 실패 시 실패횟수 증가(5회 실패시 계정 잠금)
     * @param id
     * @return
     */
    @Transactional
    public Member failCountUp (String id) {
        memberRepository.failCountUp(id);
        Member detailMember = memberRepository.findById(id);

        if (detailMember.getFailCount() == 5) {
            memberRepository.loginLocked(detailMember.getId());
        }
        return detailMember;
    }

    /**
     * 로그인 성공 시 실패횟수 초기화
     * @param id
     */
    @Transactional
    public void failCountClear(String id) {
        memberRepository.failCountClear(id);
    }
}

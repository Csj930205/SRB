package com.srb.srb.repository;

import com.srb.srb.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * 회원조회
     * @param id
     * @return
     */
    Member findById(String id);

    /**
     * 로그인 실패 시 실패 카운트 증가
     * @param id
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update Member set failCount = failCount + 1 where id = :id")
    void failCountUp(@Param("id") String id);

    /**
     * 로그인 성공시 실패 카운트 초기화
     * @param id
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update Member set failCount = 0 where id = :id")
    void failCountClear(@Param("id") String id);

    /**
     * 로그인 5회 실패시 계정 잠금
     * @param id
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update Member set enabled = false where id = :id")
    void loginLocked(@Param("id") String id);
}

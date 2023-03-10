package com.srb.srb.controller;

import com.srb.srb.domain.dto.ArticleDto;
import com.srb.srb.service.ArticleService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.srb.srb.enums.RoleEnum.ROLES.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleController {
    private final ArticleService articleService;

    /**
     * 게시글 전체 조회
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<Map<String, Object>> articleAllList() {
        Map<String, Object> result = articleService.articleAllList();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 게시글 상세 조회
     * @param seq
     * @return
     */
    @GetMapping("/detailList/{seq}")
    public ResponseEntity<Map<String, Object>> detailArticle(@PathVariable("seq") Long seq) {
        Map<String, Object> result = articleService.detailArticle(seq);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 게시글 등록
     * @param articleDto
     * @return
     */
    @RolesAllowed({관리자})
    @PostMapping("insertArticle")
    public ResponseEntity<Map<String, Object>> insertArticle(@RequestBody ArticleDto articleDto) {
        Map<String, Object> result = articleService.insertArticle(articleDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 게시글 수정
     * @param seq
     * @param articleDto
     * @return
     */
    @PatchMapping("/modify/{seq}")
    public ResponseEntity<Map<String, Object>> modifyArticle(@PathVariable("seq") Long seq, ArticleDto articleDto) {
        Map<String, Object> result = articleService.articleModify(seq, articleDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 게시글 삭제(업데이트)
     * @param seq
     * @return
     */
    @PatchMapping("/deleteArticle/{seq}")
    public ResponseEntity<Map<String, Object>> deleteArticle(@PathVariable("seq") Long seq) {
        Map<String, Object> result = articleService.articleDelete(seq);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}

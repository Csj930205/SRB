package com.srb.srb.service;

import com.srb.srb.domain.dto.ArticleDto;
import com.srb.srb.domain.entity.Article;
import com.srb.srb.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    /**
     * 게시글 리스트 전체 출력
     * @return
     */
    public Map<String, Object> articleAllList() {
        Map<String, Object> result = new HashMap<>();
        List<Article> allList = articleRepository.findAll();

        result.put("result", "success");
        result.put("code", HttpStatus.OK.value());
        result.put("articleList", allList);
        return result;
    }

    /**
     * 상세 게시글 조회
     * @param seq
     * @return
     */
    public Map<String, Object> detailArticle(Long seq) {
        Map<String, Object> result = new HashMap<>();
        Optional<Article> detailArticle = articleRepository.findById(seq);

        if (detailArticle != null) {
            result.put("result", "success");
            result.put("code", HttpStatus.OK.value());
            result.put("detailArticle", detailArticle);
        } else {
           result.put("result", "fail");
           result.put("code", HttpStatus.NOT_FOUND);
           result.put("message", "존재하지 않는 게시글 입니다.");
        }
        return result;
    }

    /**
     * 게시글 저장
     * @param articleDto
     * @return
     */
    @Transactional
    public Map<String, Object> insertArticle (ArticleDto articleDto) {
        Map<String, Object> result = new HashMap<>();
        articleRepository.save(articleDto.toEntity());

        result.put("result", "success");
        result.put("code", HttpStatus.OK.value());
        result.put("message", "게시글이 등록되었습니다.");

        return result;
    }

    /**
     * 게시글 수정
     * @param seq
     * @param articleDto
     * @return
     */
    @Transactional
    public Map<String, Object> articleModify(Long seq, ArticleDto articleDto) {
        Map<String, Object> result = new HashMap<>();
        Article article = articleRepository.findById(seq).orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        article.articleModify(articleDto);

        result.put("result", "success");
        result.put("code", HttpStatus.OK.value());
        result.put("message", "게시글 수정이 완료되었습니다.");

        return result;
    }

    /**
     * 게시글 삭제(업데이트)
     * @param seq
     * @return
     */
    @Transactional
    public Map<String, Object> articleDelete(Long seq) {
        Map<String, Object> result = new HashMap<>();
        Article article = articleRepository.findById(seq).orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        article.articleDelete();
        result.put("result", "success");
        result.put("code", HttpStatus.OK.value());
        result.put("message", "게시글이 삭제되었습니다.");

        return result;
    }

}

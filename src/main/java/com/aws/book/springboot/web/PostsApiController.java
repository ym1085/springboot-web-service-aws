package com.aws.book.springboot.web;

import com.aws.book.springboot.service.PostsService;
import com.aws.book.springboot.web.dto.PostsResponseDto;
import com.aws.book.springboot.web.dto.PostsSaveRequestDto;
import com.aws.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    /**
     * 게시글 등록 API
     * @param requestDto 제목, 내용, 작성자를 파라미터로 받는다
     * @return insert/update 된 id 값을 반환
     */
    @PostMapping("/api/v1/posts")
    public Long save(@Valid @RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    /**
     * 게시글 수정 API
     * @param id 게시글 번호
     * @param requestDto : 게시글 수정에 필요한 파라미터
     * @return 게시글 수정 성공 여부 반환
     */
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable("id") Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    /**
     * 게시글 조회 API
     * @param id 게시글 번호
     * @return 단일 게시글 반환
     */
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable("id") Long id) {
        return postsService.findById(id);
    }

    /**
     * 게시글 삭제 API
     * @param id 게시글 번호
     */
    @DeleteMapping("/api/v1/posts/{id}")
    public void delete(@PathVariable("id") Long id) {
        postsService.delete(id);
    }

}

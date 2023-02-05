package com.aws.book.springboot.web;

import com.aws.book.springboot.service.PostsService;
import com.aws.book.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    /**
     * 게시글 등록 API
     * @param requestDto : title, content, author 값을 request로 받아 저장
     * @return Long : insert/update 된 id 값을 반환
     */
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }
}

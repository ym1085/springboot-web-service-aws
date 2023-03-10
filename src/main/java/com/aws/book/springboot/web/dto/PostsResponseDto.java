package com.aws.book.springboot.web.dto;

import com.aws.book.springboot.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    /**
     * 굳이 모든 필드를 가진 생성자가 필요하지 않은 경우, Entity를 받아서 값을 셋팅한다
     * @param entity
     */
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}

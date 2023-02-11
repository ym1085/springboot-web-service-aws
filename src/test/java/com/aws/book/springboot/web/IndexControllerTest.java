package com.aws.book.springboot.web;

import com.aws.book.springboot.service.PostsService;
import com.aws.book.springboot.web.dto.PostsListResponseDto;
import com.aws.book.springboot.web.dto.PostsSaveRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @RunWith
 * - @SpringBootTest(통합 테스트)를 사용하면 'application context(IOC Container)'를 전부 로딩해서 자칫
 *   잘못하면 '무거운 프로젝트'로서의 역할을 할 수 있다.
 *
 *  - 하지만 Junit4에서 지원하는 @RunWith(SpringRunner.class)를 사용한다면 @Autowired, @MockBean에
 *    해당 되는 것들에만 'application context' 를 로딩하게 되므로 junit4에서는 필요한 조건에 맞춰서
 *    '@RunWith(SpringRunner.class)'를 사용함
 *
 *  - 즉, JUnit 프레임워크의 테스트 실행 방법을 확장할 떄 사용하는 어노테이션
 *
 */

// https://chb2005.tistory.com/63
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

    @Autowired
    private PostsService postsService;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void 메인페이지_로딩() throws Exception {
        //given
        //when
        String body = restTemplate.getForObject("/", String.class);

        //then
        assertThat(body).contains("스프링 부트");
    }

    @Test
    public void 게시글_전체_조회_테스트() throws Exception {
        //given
        //when
        saveBoardContents();

        List<PostsListResponseDto> response = postsService.findAllDesc();

        //then
        assertThat(response.size()).isGreaterThan(0);
        assertThat(response.size()).isEqualTo(2);
    }

    private void saveBoardContents() {
        postsService.save(PostsSaveRequestDto.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        postsService.save(PostsSaveRequestDto.builder()
                .title("title2")
                .content("content2")
                .author("author2")
                .build());
    }

}

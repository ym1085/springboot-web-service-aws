package com.aws.book.springboot.web;

import com.aws.book.springboot.domain.posts.Posts;
import com.aws.book.springboot.domain.posts.PostsRepository;
import com.aws.book.springboot.web.dto.PostsSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class) // Spring이 제공하는 지시자 대신 다른 지시자를 사용
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // host가 사용하지 않는 랜덤 포트 지정
public class PostsApiControllerTest {

    // https://atoz-develop.tistory.com/entry/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B6%80%ED%8A%B8-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EB%82%B4%EC%9E%A5-%EC%84%9C%EB%B2%84-%EB%9E%9C%EB%8D%A4-%ED%8F%AC%ED%8A%B8%EB%A1%9C-%EB%9D%84%EC%9A%B0%EA%B8%B0
    // Spring Boot Test - 내장 서버 랜덤 포트로 띄우기
    @LocalServerPort
    private int port;

    // MockTest 대신 사용할 수 있는 TestRestTemplate -> 스프링 컨테이너를 직접 실행 시킨다
    // restTemplate(URL, parameters, return Type)으로 지정하여 사용 한다
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다() throws Exception {
        //given
        String title = "title";
        String content = "content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList.get(0).getTitle()).isEqualTo(title);
        assertThat(postsList.get(0).getContent()).isEqualTo(content);
    }

}

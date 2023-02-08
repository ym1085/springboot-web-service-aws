package com.aws.book.springboot.web;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void 메인페이지_로딩() throws Exception {
        //given
        //when
        String body = restTemplate.getForObject("/", String.class);

        //then
        Assertions.assertThat(body).contains("스프링 부트");
    }

}

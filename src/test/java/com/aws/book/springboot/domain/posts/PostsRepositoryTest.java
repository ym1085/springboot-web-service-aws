package com.aws.book.springboot.domain.posts;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    /**
     * 테스트 완료 후 모든 데이터 제거
     */
    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() throws Exception {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";
        String author = "ymkim1085@funin.camp";

        /* builder pattern 사용하여 데이터 셋팅 */
        Posts posts = Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        //when
        postsRepository.save(posts); // 데이터 등록

        List<Posts> postList = postsRepository.findAll();

        //then
        Posts result = postList.get(0);
        Assertions.assertThat(result.getTitle()).isEqualTo(title);
        Assertions.assertThat(result.getContent()).isEqualTo(content);
    }

}

package com.aws.book.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void BaseTimeEntity_등록() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);

        // 샘플 게시글 등록
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //when
        List<Posts> all = postsRepository.findAll();

        //then
        assertThat(all.get(0).getCreatedDate()).isAfter(now);
        assertThat(all.get(0).getModifiedDate()).isAfter(now);
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
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getContent()).isEqualTo(content);
    }

}

package com.aws.book.springboot.service;

import com.aws.book.springboot.domain.posts.Posts;
import com.aws.book.springboot.domain.posts.PostsRepository;
import com.aws.book.springboot.web.dto.PostsListResponseDto;
import com.aws.book.springboot.web.dto.PostsResponseDto;
import com.aws.book.springboot.web.dto.PostsSaveRequestDto;
import com.aws.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// https://www.daleseo.com/lombok-popular-annotations/
@RequiredArgsConstructor // final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 만들어줍니다.
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    // @Transactional(readOnly = true) : readOnly = true 설정 시 트랜잭션을 읽기 전용 모드로 설정 가능
    // 예상치 못한 엔티티의 등록, 변경, 삭제 예방 가능
    // 성능 최적화 역시 가능함
    // 최상단 Service 위치에 @Transactional을 선언하여 공통적으로 사용 가능하지만, 메서드별로 처리하는것도 나쁘지는 않음

    /**
     * 게시글 저장
     * @param requestDto
     * @return
     */
    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    /**
     * 게시글 수정
     * @param id
     * @param requestDto
     * @return
     */
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        // JPA의 영속성 컨텍스트 때문이다, 영속성 컨텍스트는 -> 엔티티를 영구 저장하는 환경
        // entityManager.persist(entity); -> DB에 저장하는 것이 아니라 영속성 컨텍스트에 저장한다는 의미

        // id를 기반으로 하여 게시글 조회, 없을 경우 Exception 반환
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        // Posts 안에 public void update(String title, String content) 메서드가 존재
        // Entity 안에 메서드를 넣어서 처리 하였음, DDD 구조인가?
        // TODO: 체크 포인트
        // DB에 쿼리를 날리는 부분이 존재하지 않는다?
        // 단순히 posts 엔티티를 업데이트 하는 구문밖에 존재하지 않는다..
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    /**
     * 단일 게시글 조회
     * @param id
     * @return
     */
    public PostsResponseDto findById(Long id) {
        // id를 기반으로 하여 게시글 조회, 없을 경우 Exception 반환
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        // Response 객체인 PostsResponseDto의 생성자에 Posts:posts 변수를 인수로 준다
        return new PostsResponseDto(posts);
    }

    /**
     * 전체 게시글 조회
     * @return
     */
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
//                .map(posts -> new PostsListResponseDto(posts))
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

}

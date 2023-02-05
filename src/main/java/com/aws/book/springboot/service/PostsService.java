package com.aws.book.springboot.service;

import com.aws.book.springboot.domain.posts.PostsRepository;
import com.aws.book.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    // @Transactional(readOnly = true) : readOnly = true 설정 시 트랜잭션을 읽기 전용 모드로 설정 가능
    // 예상치 못한 엔티티의 등록, 변경, 삭제 예방 가능
    // 성능 최적화 역시 가능함
    // 최상단 Service 위치에 @Transactional을 선언하여 공통적으로 사용 가능하지만, 메서드별로 처리하는것도 나쁘지는 않음

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}

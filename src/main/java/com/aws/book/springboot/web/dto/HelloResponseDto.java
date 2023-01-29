package com.aws.book.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// reference: https://dreamcoding.tistory.com/83
@Getter // 선언된 모든 필드의 Getter 메서드 생성
@RequiredArgsConstructor // 선언된 모든 final 필드가 포함된 생성자 생성 + final이 없는 필드 생성자에 포함 안함
public class HelloResponseDto {

    private final String name; // 이름
    private final int amount; // 양

    // 실제로는 @RequiredArgsConstructor 선언하면 아래와 같이 생성자가 생성됨
    // final로 선언이 되어있는 필드만 초기화 시킨다
    // 스프링 DI의 방법 중에서 생성자 주입을 임의의 코드 없이 자동 설정
    //  - [x] @Autowired 필드 주입
    //  - [x] setter 주입
    //  - [o] 생성자 주입 -> 현재는 생성자 주입 사용
    /*@Autowired
    public HelloResponseDto(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }*/
}

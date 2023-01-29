package com.aws.book.springboot.web.dto;

import org.assertj.core.api.Assertions;
import org.junit.Test;

// 어노테이션을 사용하지 않고도, 기본 자바 로직만으로도 테스트가 가능
public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        Assertions.assertThat(dto.getName()).isEqualTo(name);
        Assertions.assertThat(dto.getAmount()).isEqualTo(amount);
    }
}

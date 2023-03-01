package com.aws.book.springboot.config.auth.dto;

import com.aws.book.springboot.domain.user.Role;
import com.aws.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

//https://okky.kr/articles/1085064
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,
                           String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    /**
     *
     * @param registrationId : 현재 로그인 진행 중인 서비스 구분 코드
     * @param userNameAttributeName : 소셜 로그인 구분 코드 PK 느낌 (GOOGLE : sub)
     * @param attributes : OAuth2User에서 반환하는 값 (OAuth2User.getAttributes())
     * @return
     */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name")) // 사용자명
                .email((String) attributes.get("email")) // 사용자 이메일
                .picture((String) attributes.get("picture")) // 사용자 프로필
                .attributes(attributes) // OAuth2User의 모든 속성 Map 형태로 저장
                .nameAttributeKey(userNameAttributeName) // 소셜 로그인 구분 코드 PK 느낌
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}

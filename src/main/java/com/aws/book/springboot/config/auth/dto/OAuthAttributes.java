package com.aws.book.springboot.config.auth.dto;

import com.aws.book.springboot.domain.user.Role;
import com.aws.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

//https://okky.kr/articles/1085064
@Slf4j
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
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
        log.info("registrationId = {}, userNameAttributeName = {}, attributes = {}", registrationId, userNameAttributeName, attributes);
        if ("naver".equalsIgnoreCase(registrationId)) {
            return ofNaver("id", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    /* OAuth2 Google */
    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name")) // 사용자명
                .email((String) attributes.get("email")) // 사용자 이메일
                .picture((String) attributes.get("picture")) // 사용자 프로필
                .attributes(attributes) // OAuth2User의 모든 속성 Map 형태로 저장
                .nameAttributeKey(userNameAttributeName) // 소셜 로그인 구분 코드 PK 느낌
                .build();
    }

    /* OAuth2 Naver */
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response"); // response 값을 가져온다, ...naver.user_name_attribute=response
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
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

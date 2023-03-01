package com.aws.book.springboot.config.auth;

import com.aws.book.springboot.config.auth.dto.OAuthAttributes;
import com.aws.book.springboot.config.auth.dto.SessionUser;
import com.aws.book.springboot.domain.user.User;
import com.aws.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    /**
     * https://developers.google.com/identity/protocols/oauth2?hl=ko
     * @param userRequest the user request
     * @return OAuth2User
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /**
         * {
         *      sub=113719334175736751803,
         *      name=YOUNGMIN KIM,
         *      given_name=YOUNGMIN,
         *      family_name=KIM,
         *      picture=https://lh3.googleusercontent.com/a/AGNmyxY-pj9YUQH33KKGrX-JmE9EE9Mx0L8ZZ_DXvVTwfg=s96-c,
         *      email=youngmin1085@gmail.com,
         *      email_verified=true,
         *      locale=ko
         *  }
         */
        log.info("oAuth2User = {}", oAuth2User.getAttributes());

        // 현재 로그인 진행 중인 서비스를 구분하는 코드
        // 현재는 구글 로그인 이지만 추후 네이버, 카카오 로그인 연동 시 구분하기 위해 사용
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("registrationId = {}", registrationId); // google

        // OAuth2 로그인 진행 시 키가 되는 필드값, Primary Key와 같은 의미
        // 구글의 경우 기본적으로 코드 지원, 네이버 카카오 등은 기본 지원하지 않음
        // 구글의 기본 코드는 "sub" 다 (네이버는 id)
        // 추후 구글, 네이버, 카카오 로그인 동시 지원 시 코드 사용
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        log.info("userNameAttributeName = {}", userNameAttributeName); // sub

        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담은 클래스
        // 이후 네이버 등 다른 소셜 로그인도 해당 클래스 사용
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        log.info("OAuth attributes, attributes = {}", attributes);

        // 구글 사용자 정보가 업데이트 되었을 때를 대비하여 update 기능도 같이 구현
        // -> user에 값이 있다면 update, 값이 없다면 save
        User user = saveOrUpdate(attributes);

        // 세션에 사용자 정보를 저장하기 위한 로직
        // SessionUser Dto에 세션 정보를 저장한다
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    /**
     * 클라이언트가 소셜 로그인 계정의 이름, 프로필을 업데이트 할 때 User 엔티티 상태 저장 및 업데이트 방어 로직
     * @param attributes
     * @return User : 저장 혹은 업데이트 된 유저 정보를 반환
     */
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail()) // email 기반 조회
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}

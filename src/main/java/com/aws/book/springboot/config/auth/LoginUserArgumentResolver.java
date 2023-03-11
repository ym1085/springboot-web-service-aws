package com.aws.book.springboot.config.auth;

import com.aws.book.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

//https://jhkang-tech.tistory.com/49
@Slf4j
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    /**
     * 바인딩할 클래스를 지정해주는 메서드
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) { // 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단
        log.info("supportsParameter, parameter = {}", parameter);

        // parameter.getParameterAnnotation(ClassType.class) != null => 어노테이션 타입이 LoginUser.class와 동일?
        // 바인딩할 클래스(여기서는 Annotation)이 LoginUser.class 인가?
        // 조건에 맞으면 HandlerMethodArgumentResolver 의 구현체가 지정한 값으로 해당 메서드의 파라미터로 넘길 수 있다
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;

        // 클래스 타입이 SessionUser와 동일?
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        // true, false 여부에 따라서 resolveArgument 호출
        return isLoginUserAnnotation && isUserClass;
    }

    /**
     * 바인딩할 객체를 조작할 수 있는 메서드. 예를 들면 파라미터에 전달할 객체를 생성, 여기서는 세션에서 객체를 가져온다
     */
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        log.info("resolveArgument, parameter = {}", parameter);
        return httpSession.getAttribute("user");
    }
}

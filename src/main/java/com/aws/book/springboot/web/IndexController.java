package com.aws.book.springboot.web;

import com.aws.book.springboot.config.auth.dto.SessionUser;
import com.aws.book.springboot.service.PostsService;
import com.aws.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession; // 메인 화면에서 session 값 사용을 위해 추가

    /**
     * 메인 화면 출력 API
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());

        SessionUser user = (SessionUser) httpSession.getAttribute("user"); // CustomOAuth2UserService 클래스에서 session 값 셋팅
        if (user != null) {
            model.addAttribute("userName", user.getName());
        } else {
            log.warn("Cannot find userName in HttpSession...");
        }
        return "index";
    }

    /**
     * 글 등록 화면 출력 API
     * @return
     */
    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    /**
     * 글 수정 화면 출력 API
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable("id") Long id, Model model) {
        PostsResponseDto responseDto = postsService.findById(id);
        model.addAttribute("post", responseDto);
        return "posts-update";
    }
}

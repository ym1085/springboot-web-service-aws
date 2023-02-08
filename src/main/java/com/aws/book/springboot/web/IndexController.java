package com.aws.book.springboot.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        log.info("Enter index page");
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save"; // 글 등록 화면으로 이동
    }
}

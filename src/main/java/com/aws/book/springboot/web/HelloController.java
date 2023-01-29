package com.aws.book.springboot.web;

import com.aws.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @since   20230129
 * @author  ymkim
 * @desc    sample controller for http test
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
    
    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, 
                                     @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}

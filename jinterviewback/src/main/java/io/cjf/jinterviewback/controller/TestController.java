package io.cjf.jinterviewback.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@CrossOrigin
public class TestController {

    @GetMapping("/hello")
    public String hello(@RequestParam(required = false) String pp) {
        return "aaa";
    }
}

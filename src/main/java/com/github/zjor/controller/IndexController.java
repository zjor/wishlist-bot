package com.github.zjor.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/")
public class IndexController {

    private final String version;

    public IndexController(@Value("${VCS_REF:not set}") String version) {
        this.version = version;
    }

    @GetMapping("/")
    public Map<String, String> index() {
        return Collections.singletonMap("status", "ok");
    }

    @GetMapping("version")
    public Map<String, String> version() {
        return Collections.singletonMap("version", version);
    }

}

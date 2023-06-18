package com.github.zjor.controller;

import com.github.zjor.controller.dto.JUser;
import com.github.zjor.domain.User;
import com.github.zjor.ext.spring.auth.AuthUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

    @GetMapping("me")
    public JUser me(@AuthUser User user) {
        return JUser.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .imageUrl(user.getImageUrl())
                .build();
    }

}

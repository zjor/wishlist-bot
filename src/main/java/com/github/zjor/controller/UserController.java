package com.github.zjor.controller;

import com.github.zjor.controller.dto.JUser;
import com.github.zjor.domain.User;
import com.github.zjor.ext.spring.auth.AuthUser;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.zjor.controller.XHttpHeaders.X_TELEGRAM_USER;

@SecurityRequirements({@SecurityRequirement(name = X_TELEGRAM_USER)})
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

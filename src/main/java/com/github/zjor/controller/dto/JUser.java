package com.github.zjor.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.zjor.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JUser {

    @JsonProperty("username")
    private String username;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("imageUrl")
    private String imageUrl;

    public static class Converter {
        public static JUser build(User user) {
            return JUser.builder()
                    .username(user.getUsername())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .imageUrl(user.getImageUrl())
                    .build();
        }
    }

}

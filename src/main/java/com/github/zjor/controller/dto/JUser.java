package com.github.zjor.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.zjor.domain.User;
import com.github.zjor.domain.jooq.tables.records.UsersRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JUser {

    @JsonProperty("extId")
    private String extId;

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
                    .extId(user.getExtId())
                    .username(user.getUsername())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .imageUrl(user.getImageUrl())
                    .build();
        }

        public static JUser build(UsersRecord r) {
            return JUser.builder()
                    .extId(r.getExtId())
                    .username(r.getUsername())
                    .firstName(r.getFirstName())
                    .lastName(r.getLastName())
                    .imageUrl(r.getImageUrl())
                    .build();
        }
    }

}

package com.github.zjor.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;

    /**
     * External id, e.g. telegram id
     */
    private String extId;
    private String username;
    private String firstName;
    private String lastName;

}

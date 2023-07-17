package com.github.zjor.service;

import com.github.zjor.domain.jooq.tables.records.UsersRecord;
import org.jooq.DSLContext;

import java.util.List;

import static com.github.zjor.domain.jooq.tables.Users.USERS;
import static org.jooq.impl.DSL.or;

public class UserSearchService {

    private final DSLContext dsl;

    public UserSearchService(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<UsersRecord> findBySingleInput(String input) {
        if (input == null || input.length() < 2) {
            return List.of();
        }
        return dsl.selectFrom(USERS)
                .where(or(
                        USERS.FIRST_NAME.startsWithIgnoreCase(input),
                        USERS.LAST_NAME.startsWithIgnoreCase(input),
                        USERS.USERNAME.startsWithIgnoreCase(input)
                ))
                .orderBy(USERS.CREATED_AT.desc())
                .limit(15)
                .fetch();
    }

}

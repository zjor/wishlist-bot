package com.github.zjor.ext.spring.auth;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Base64;

@Slf4j
public class BasicAuthFilter implements Filter {

    public static final String BASIC_PREFIX = "Basic ";

    private final String login;
    private final String password;

    public BasicAuthFilter(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) res;
        String authorization = httpReq.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization != null) {
            if (authorization.toLowerCase().startsWith(BASIC_PREFIX.toLowerCase())) {
                authorization = authorization.substring(BASIC_PREFIX.length());
                authorization = new String(Base64.getDecoder().decode(authorization));
                var credentials = authorization.split(":");
                if (credentials[0].equals(login) && credentials[1].equals(password)) {
                    chain.doFilter(req, res);
                    return;
                } else {
                    log.info("Bad credentials: {}", authorization);
                }
            } else {
                log.info("Wrong auth type: {}", authorization);
            }
        }
        log.info("Basic auth required");
        httpRes.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"WishListBot\", charset=\"UTF-8\"");
        httpRes.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}

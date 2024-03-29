package com.github.zjor.controller;

import com.github.zjor.controller.dto.JProfileStatsResponse;
import com.github.zjor.controller.dto.JUser;
import com.github.zjor.domain.ItemStatus;
import com.github.zjor.domain.User;
import com.github.zjor.domain.WishlistItem;
import com.github.zjor.ext.spring.auth.AuthUser;
import com.github.zjor.repository.WishlistItemRepository;
import com.github.zjor.service.UserSearchService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.zjor.controller.XHttpHeaders.X_TELEGRAM_USER;

@SecurityRequirements({@SecurityRequirement(name = X_TELEGRAM_USER)})
@RestController
@RequestMapping("api/user")
public class UserController {

    private final WishlistItemRepository wishlistItemRepository;
    private final UserSearchService userSearchService;

    public UserController(
            WishlistItemRepository wishlistItemRepository,
            UserSearchService userSearchService) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.userSearchService = userSearchService;
    }

    @GetMapping("me")
    public JUser me(@AuthUser User user) {
        return JUser.Converter.build(user);
    }

    @GetMapping("me/stats")
    public JProfileStatsResponse getStats(@AuthUser User user) {
        int all = 0;
        int _public = 0;
        int done = 0;

        for (WishlistItem item : wishlistItemRepository.findByOwner(user)) {
            if (item.getStatus() != ItemStatus.ARCHIVED) {
                all++;

                if (item.isPublic() && item.getStatus() != ItemStatus.DONE) {
                    _public++;
                }

                if (item.getStatus() == ItemStatus.DONE) {
                    done++;
                }
            }
        }

        return new JProfileStatsResponse(all, _public, done);
    }

    @GetMapping("search")
    public List<JUser> search(@RequestParam("q") String query) {
        return userSearchService.findBySingleInput(query).stream()
                .map(JUser.Converter::build)
                .collect(Collectors.toList());
    }

}

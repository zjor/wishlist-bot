package com.github.zjor.domain;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Wishlist item status
 */
public enum ItemStatus {
    OPEN(false),
    IN_PROGRESS(false),
    DONE(true),
    ARCHIVED(true);

    private final static Map<ItemStatus, Set<ItemStatus>> t = Map.of(
            OPEN, Set.of(IN_PROGRESS, DONE, ARCHIVED),
            IN_PROGRESS, Set.of(OPEN, DONE, ARCHIVED)
    );

    public final boolean isTerminal;

    ItemStatus(boolean isTerminal) {
        this.isTerminal = isTerminal;
    }

    public boolean canTransitionTo(ItemStatus to) {
        return t.containsKey(this) && t.get(this).contains(to);
    }

    public List<Pair<ItemStatus, Boolean>> getAllowedStatuses() {
        return getAllowedStatusesFrom(this);
    }

    public static List<Pair<ItemStatus, Boolean>> getAllowedStatusesFrom(ItemStatus from) {
        return Arrays.stream(ItemStatus.values())
                .map(to -> Pair.of(to, from.canTransitionTo(to)))
                .collect(Collectors.toList());
    }

}

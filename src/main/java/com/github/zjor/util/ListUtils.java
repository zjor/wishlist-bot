package com.github.zjor.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListUtils {

    public static List<String> parse(String items) {
        if (items == null || items.length() == 0) {
            return List.of();
        }
        return Arrays.asList(items.split(",")).stream()
                .map(s -> s.trim())
                .collect(Collectors.toList());
    }
}

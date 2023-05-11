package com.github.zjor.util;

import org.apache.commons.validator.routines.UrlValidator;

public class UrlUtils {

    private static final UrlValidator urlValidator = new UrlValidator();

    public static boolean isValidUrl(String url) {
        return urlValidator.isValid(url);
    }

}

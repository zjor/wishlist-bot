package com.github.zjor.service;

import com.github.zjor.ext.spring.aop.Log;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

public class ReferralUrlService {

    private static final List<Pattern> AMAZON_URL_PATTERNS = List.of(
            Pattern.compile("dp\\/([A-Za-z0-9]{10})"),
            Pattern.compile("gp\\/([A-Za-z0-9]{10})"),
            Pattern.compile("product\\/([A-Za-z0-9]{10})"));

    private static final String AMAZON_REFERRAL_URL_TEMPLATE = "http://www.amazon.com/dp/%s/ref=nosim?tag=%s";

    private final String amazonStoreId;

    public ReferralUrlService(String amazonStoreId) {
        this.amazonStoreId = amazonStoreId;
    }

    @Log
    public String getReferralUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }

        if (url.toLowerCase().contains("amazon")) {
            return getAmazonReferralUrl(url);
        }

        return url;
    }

    private String getAmazonReferralUrl(@NotNull String url) {
        String productId = null;
        for (var p : AMAZON_URL_PATTERNS) {
            var m = p.matcher(url);
            if (m.find()) {
                productId = m.group(1);
                break;
            }
        }
        if (productId != null) {
            return String.format(AMAZON_REFERRAL_URL_TEMPLATE, productId, amazonStoreId);
        }
        return url;
    }


}

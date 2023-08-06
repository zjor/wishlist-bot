package com.github.zjor.service;

import org.junit.Assert;
import org.junit.Test;

public class ReferralUrlServiceTest {

    private ReferralUrlService referralUrlService = new ReferralUrlService("alice");

    @Test
    public void shouldConvertToReferralUrl() {
        var url = "https://www.amazon.com/HiLetgo-ESP-32S-Bluetooth-Wireless-ESP-WROOM-32/dp/B077KJNVFP/ref=pd_ci_mcx_mh_mcx_views_0?pd_rd_w=I96bw&content-id=amzn1.sym.0250fb24-4363-44d0-b635-ac15f859c3b5%3Aamzn1.symc.40e6a10e-cbc4-4fa5-81e3-4435ff64d03b&pf_rd_p=0250fb24-4363-44d0-b635-ac15f859c3b5&pf_rd_r=Y5V393KACJFJKATS1RRH&pd_rd_wg=WoJTh&pd_rd_r=6b2a0e92-3f20-42c7-a572-3a8c8a8c2727&pd_rd_i=B077KJNVFP";
        var referralUrl = referralUrlService.getReferralUrl(url);
        Assert.assertEquals("https://www.amazon.com/dp/B077KJNVFP/ref=nosim?tag=alice", referralUrl);
    }

    @Test
    public void shouldKeepAmazonUrlAsItIs() {
        var url = "https://www.amazon.com/some-product";
        var referralUrl = referralUrlService.getReferralUrl(url);
        Assert.assertEquals(url, referralUrl);
    }

    @Test
    public void shouldKeepNonAmazonAsItIs() {
        var url = "https://www.google.com/some-product";
        var referralUrl = referralUrlService.getReferralUrl(url);
        Assert.assertEquals(url, referralUrl);
    }

}
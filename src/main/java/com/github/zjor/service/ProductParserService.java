package com.github.zjor.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Evaluator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;

/*
- extract price
 */

@Slf4j
public class ProductParserService {

    private static String strip(String input) {
        if (input != null && input.startsWith("Amazon.com: ")) {
            return input.substring("Amazon.com: ".length());
        }

        if (input != null && input.startsWith("Amazon.com : ")) {
            return input.substring("Amazon.com : ".length());
        }
        return input;
    }

    private static String lcp(String a, String b) {
        if (a == null || b == null || a.length() == 0 || b.length() == 0) {
            return "";
        }
        int i = 0;
        while (i < Math.min(a.length(), b.length()) && a.charAt(i) == b.charAt(i)) {
            i++;
        }
        return a.substring(0, i);
    }

    public static void main(String[] args) throws IOException {

        var htmlFile = new File("./tools/parser/out-3.html");
        Document doc = Jsoup.parse(htmlFile);

        var metaData = new HashMap<String, String>();
        for (var el: doc.select(new Evaluator.Tag("meta"))) {
            var name = el.attr("name");
            if (name == null || name.length() == 0) {
                continue;
            }
            metaData.put(name, strip(el.attr("content")));
        }

        log.info("Title: {}", metaData.get("title"));
        log.info("Description: {}", metaData.get("description"));

        var images = doc.select(new Evaluator.Tag("img"));
        var imgQueue = new PriorityQueue<Pair<Integer, String>>((o1, o2) -> o2.getLeft() - o1.getLeft());
        var title = metaData.get("title");

        for (var img: images) {
            var alt = img.attr("alt");
            if (alt == null || alt.length() == 0) {
                continue;
            }
            var src = img.attr("src");
            var dataSrc = img.attr("data-src");
            imgQueue.add(Pair.of(lcp(alt, title).length(), dataSrc.length() > 0 ? dataSrc : src));
        }

        log.info("{}", imgQueue.peek());

        for (var el: doc.select(".priceToPay")) {
            var ch = el.getAllElements();
            if (ch.size() > 1) {
                log.info("Price: {}", ch.get(1).text());
            }
        }

    }
}

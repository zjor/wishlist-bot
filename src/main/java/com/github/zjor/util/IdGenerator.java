package com.github.zjor.util;

import org.hashids.Hashids;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private static final int MIN_LENGTH = 12;
    private static Hashids hashids = new Hashids(String.valueOf(System.currentTimeMillis()), MIN_LENGTH);
    private static Random random = new Random(System.currentTimeMillis());
    private static AtomicLong counter = new AtomicLong(0);

    public static String nextId() {
        return hashids.encode(
                System.currentTimeMillis(),
                random.nextLong() % 9007199254740992L,
                counter.incrementAndGet());
    }
}

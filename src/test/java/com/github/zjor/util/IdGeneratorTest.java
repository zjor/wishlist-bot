package com.github.zjor.util;

import org.junit.Assert;
import org.junit.Test;

public class IdGeneratorTest {

    @Test
    public void shouldGenerateUniqueId() {
        Assert.assertNotEquals(IdGenerator.nextId(), IdGenerator.nextId());
    }

}
package com.github.zjor.util;


import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ListUtilsTest {

    @Test
    public void shouldParseCommaSeparatedList() {
        List<String> items = ListUtils.parse("one, two, three");
        Assert.assertEquals(List.of("one", "two", "three"), items);
    }

    @Test
    public void shouldParseCommaSeparatedListOfOneItem() {
        List<String> items = ListUtils.parse("one");
        Assert.assertEquals(List.of("one"), items);
    }

    @Test
    public void shouldHandleEmptyString() {
        List<String> items = ListUtils.parse("");
        Assert.assertEquals(List.of(), items);
    }

    @Test
    public void shouldHandleNull() {
        List<String> items = ListUtils.parse(null);
        Assert.assertEquals(List.of(), items);
    }

    @Test
    public void shouldReturnNVL() {
        Assert.assertEquals("alice", ListUtils.nvl(null, null, "alice", "bob"));
        Assert.assertNull(ListUtils.nvl(null, null));
        Assert.assertNull(ListUtils.nvl());
        Assert.assertEquals("alice", ListUtils.nvl("alice"));
    }

}
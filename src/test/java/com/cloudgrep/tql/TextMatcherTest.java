package com.cloudgrep.tql;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TextMatcherTest {

    @Test
    public void testSingleKeyword() throws IOException {
        TextMatcher testee = new TextMatcher("lalala");
        assertTrue(testee.match("h iihlalala gbig uyguguf"));
        assertFalse(testee.match("h ii= gbig uyguguf"));
        assertTrue(testee.match("h iihlalala gbig uyguguf"));
    }


    @Test
    public void testAND() throws IOException {
        TextMatcher testee = new TextMatcher("lalala AND \"中文かな 한글\"");
        assertFalse(testee.match("h iihlalala gbig uyguguf"));
        assertFalse(testee.match("h ii= bibiligbig uyguguf"));
        assertTrue(testee.match("h iih lalala gbig 中文かな 한글 uyguguf"));
    }


    @Test
    public void testOR() throws IOException {
        TextMatcher testee = new TextMatcher("lalala OR \"中文かな 한글\"");
        assertTrue(testee.match("h iihlalala gbig uyguguf"));
        assertFalse(testee.match("h ii= bibiligbig uyguguf"));
        assertTrue(testee.match("h igbig 中文かな 한글uyguguf"));
    }
}

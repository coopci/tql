package com.cloudgrep.tql;

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TextMatcherTest {

    @Test
    public void testSingleKeyword() throws IOException {
        TextMatcher testee = new TextMatcher("lalala");
        assertTrue(testee.match("h iihlalala gbig uyguguf").isMatched());
        assertFalse(testee.match("h ii= gbig uyguguf").isMatched());
        assertTrue(testee.match("h iihlalala gbig uyguguf").isMatched());
    }


    // 这个测试在windows上用maven跑这个测试会失败，但是在ec2 centos上用maven跑可以成功，在intelli里也能pass，看上去像是 한글 导致的。
    // 如果删掉 한글，在windows上用maven跑 也可以pass。
    // 在windows上的命令行里和git bash里的结果一样。gitbash的locale是 en_US.UTF-8
    @Test
    public void testAND() throws IOException {
        TextMatcher testee = new TextMatcher("lalala AND \"中文かな 한글\"");
        assertFalse(testee.match("h iihlalala gbig uyguguf").isMatched());
        assertFalse(testee.match("h ii= bibiligbig uyguguf").isMatched());
        assertTrue(testee.match("h iih lalala gbig 中文かな 한글 uyguguf").isMatched());
    }

    // 这个测试在windows上用maven跑这个测试会失败，但是在ec2 centos上用maven跑可以成功，看上去像是 한글 导致的。
    @Test
    public void testOR() throws IOException {
        TextMatcher testee = new TextMatcher("lalala OR \"中文かな 한글\"");
        assertTrue(testee.match("h iihlalala gbig uyguguf").isMatched());
        assertFalse(testee.match("h ii= bibiligbig uyguguf").isMatched());
        assertTrue(testee.match("h igbig 中文かな 한글uyguguf").isMatched());
    }

    @Test
    public void testParseAndNumber() throws IOException {
        TextMatcher testee = new TextMatcher("sdf  | parsejson | sdf = 66");
        TextMatcher.Result r = testee.match("{'sdf': 66.0}");
        assertTrue(r.isMatched());

        assertEquals("66.0",
                r.getFields().get("sdf").toString()
        );
    }
}

package com.cloudgrep.tql;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class TextMatchingVisitorTest {

    @Test
    public void testMatchAndParsejson() throws IOException {

        ByteArrayInputStream input = new ByteArrayInputStream("sdf  | parsejson".getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.PipelineContext rctx = parser.pipeline();


        TextMatchingVisitor eval = new TextMatchingVisitor("{'sdf': 'akhd'}");
        Boolean matched = eval.visit(rctx);
        assertTrue(matched);

        assertEquals(1, eval.getParsed().size());
        assertEquals("akhd", eval.getParsed().get("sdf"));


    }
    @Test
    public void testNotMatchBeforeParse() throws IOException {


        ByteArrayInputStream input = new ByteArrayInputStream("hdohg  | parsejson".getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.PipelineContext rctx = parser.pipeline();


        TextMatchingVisitor eval = new TextMatchingVisitor("{'sdf': 'akhd'}");
        Boolean matched = eval.visit(rctx);
        // not match because the text dosen't contain hdohg
        assertFalse(matched);

        // parsejson didn't executed because match failed before parsejon
        assertNull( eval.getParsed());
    }
}

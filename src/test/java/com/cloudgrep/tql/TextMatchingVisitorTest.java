package com.cloudgrep.tql;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

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



    @Test
    public void testKvStringGrepMatch() throws IOException {


        ByteArrayInputStream input = new ByteArrayInputStream("akhd  | parsejson | sdf : \"akh\"".getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.PipelineContext rctx = parser.pipeline();


        TextMatchingVisitor eval = new TextMatchingVisitor("{'sdf': 'akhd'}");
        Boolean matched = eval.visit(rctx);
        // not match because the text dosen't contain hdohg
        assertTrue(matched);

        // parsejson didn't executed because match failed before parsejon
        assertEquals(1, eval.getParsed().size());
    }

    @Test
    public void testKvStringGrepNotMatch() throws IOException {


        ByteArrayInputStream input = new ByteArrayInputStream("akhd  | parsejson | sdf : \"akhd1\"".getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.PipelineContext rctx = parser.pipeline();


        TextMatchingVisitor eval = new TextMatchingVisitor("{'sdf': 'akhd'}");
        Boolean matched = eval.visit(rctx);
        // not match because the text dosen't contain hdohg
        assertFalse(matched);

        // parsejson didn't executed because match failed before parsejon
        assertEquals(1, eval.getParsed().size());
    }



    @Test
    public void testKvStringequalMatch() throws IOException {


        ByteArrayInputStream input = new ByteArrayInputStream("akhd  | parsejson | sdf := \"akhd\"".getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.PipelineContext rctx = parser.pipeline();


        TextMatchingVisitor eval = new TextMatchingVisitor("{'sdf': 'akhd'}");
        Boolean matched = eval.visit(rctx);
        // not match because the text dosen't contain hdohg
        assertTrue(matched);

        // parsejson didn't executed because match failed before parsejon
        assertEquals(1, eval.getParsed().size());
    }



    @Test
    public void testKvStringequalNotMatch() throws IOException {


        ByteArrayInputStream input = new ByteArrayInputStream("akhd  | parsejson | sdf := \"akh\"".getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.PipelineContext rctx = parser.pipeline();


        TextMatchingVisitor eval = new TextMatchingVisitor("{'sdf': 'akhd'}");
        Boolean matched = eval.visit(rctx);
        // akh != akhd
        assertFalse(matched);

        // parsejson didn't executed because match failed before parsejon
        assertEquals(1, eval.getParsed().size());
    }



    @Test
    public void testKvNumberequalMatch() throws IOException {


        ByteArrayInputStream input = new ByteArrayInputStream("sdf  | parsejson | sdf = 66".getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.PipelineContext rctx = parser.pipeline();


        TextMatchingVisitor eval = new TextMatchingVisitor("{'sdf': 66.0}");
        Boolean matched = eval.visit(rctx);
        // 66 == 66.0
        assertTrue(matched);

        // parsejson didn't executed because match failed before parsejon
        assertEquals(1, eval.getParsed().size());
    }

    @Test
    public void testKvNumberequalNotMatch() throws IOException {


        ByteArrayInputStream input = new ByteArrayInputStream("sdf  | parsejson | sdf = 6".getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.PipelineContext rctx = parser.pipeline();


        TextMatchingVisitor eval = new TextMatchingVisitor("{'sdf': 66.0}");
        Boolean matched = eval.visit(rctx);
        // 6 != 66.0
        assertFalse(matched);

        // parsejson didn't executed because match failed before parsejon
        assertEquals(1, eval.getParsed().size());
    }





    @Test
    public void testAdhoc()  {

        assertEquals(0, new BigDecimal("100.0").compareTo(new BigDecimal(100)));
    }



    @Test
    public void testKeyword() throws IOException {


        ByteArrayInputStream input = new ByteArrayInputStream(" \"and\"".getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.PipelineContext rctx = parser.pipeline();


        TextMatchingVisitor eval = new TextMatchingVisitor("{'sdf': 'and'}");
        Boolean matched = eval.visit(rctx);
        // not match because the text dosen't contain hdohg
        assertTrue(matched);

    }
}

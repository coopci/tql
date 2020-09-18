package com.cloudgrep.tql;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class VisitorDemo {

    public static void main(String[] args) throws IOException {

        // simpleQueryDemo();
        pipelineDemo1();
        pipelineDemo2();
    }

    public static void simpleQueryDemo() throws IOException {
        // ANTLRInputStream input = new ANTLRInputStream(System.in);

        ByteArrayInputStream input = new ByteArrayInputStream("sdf AND \"中文かな 한글\" AND 中文かな한글".getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.QueryContext rctx = parser.query();


        TextMatchingVisitor eval = new TextMatchingVisitor("sdfgyugihei中文かな한글中文かな 한글");
        Boolean matched = eval.visit(rctx);
        System.out.println("matched: " + matched);

    }


    public static void  pipelineDemo1() throws IOException {
        // ANTLRInputStream input = new ANTLRInputStream(System.in);

        ByteArrayInputStream input = new ByteArrayInputStream("sdf  | parsejson".getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.PipelineContext rctx = parser.pipeline();


        TextMatchingVisitor eval = new TextMatchingVisitor("{'sdf': 'akhd'}");
        Boolean matched = eval.visit(rctx);
        System.out.println("matched: " + matched);
        System.out.println("eval.getParsed(should not be null): " + eval.getParsed());
    }



    public static void  pipelineDemo2() throws IOException {
        // ANTLRInputStream input = new ANTLRInputStream(System.in);

        ByteArrayInputStream input = new ByteArrayInputStream("hdohg  | parsejson".getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.PipelineContext rctx = parser.pipeline();


        TextMatchingVisitor eval = new TextMatchingVisitor("{'sdf': 'akhd'}");
        Boolean matched = eval.visit(rctx);
        System.out.println("matched: " + matched);
        System.out.println("eval.getParsed( should be null): " + eval.getParsed());

    }
}

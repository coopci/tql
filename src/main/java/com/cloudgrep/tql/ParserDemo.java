package com.cloudgrep.tql;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ParserDemo {
    public static void demoT() throws IOException {
        // ANTLRInputStream input = new ANTLRInputStream(System.in);

        ByteArrayInputStream input = new ByteArrayInputStream("call sdf;".getBytes());
        TLexer lexer = new TLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TParser parser = new TParser(tokens);
        TParser.RContext rctx = parser.r();

        System.out.println(rctx.toStringTree());

    }

    public static void demoPipeline() throws IOException {

        ByteArrayInputStream input = new ByteArrayInputStream("gdsjfg gugyu | parsejson".getBytes());
        TLexer lexer = new TLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.PipelineContext rctx = parser.pipeline();

        System.out.println(rctx.toStringTree());
    }
    public static void main(String[] args) throws IOException {
       // demoT();
       // demoTQL();
        demoPipeline();
    }


    public static void demoTQL() throws IOException {

        ByteArrayInputStream input = new ByteArrayInputStream("sdf AND \"中文かな 한글\" AND 中文かな한글".getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.QueryContext rctx = parser.query();

        System.out.println(rctx.toStringTree());
    }

}

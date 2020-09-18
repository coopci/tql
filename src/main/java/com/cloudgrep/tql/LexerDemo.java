package com.cloudgrep.tql;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class LexerDemo {
    public static void demoT() throws IOException {

        ByteArrayInputStream input = new ByteArrayInputStream("call sdf;".getBytes());
        TLexer lexer = new TLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        tokens.fill();
        for(Token t : tokens.getTokens()) {
            System.out.println(t);
            System.out.println(t.getText());
        }

    }

    public static void demoTQL() throws IOException {

        ByteArrayInputStream input = new ByteArrayInputStream("call sdf AND hwhh ANDX \"中文かな 한글\"  中文かな한글".getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        tokens.fill();
        for(Token t : tokens.getTokens()) {
            System.out.println(t);
            System.out.println(t.getText());
        }

    }

    public static void main(String[] args) throws IOException {
        //demoT();
        demoTQL();
    }
}

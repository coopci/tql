package com.cloudgrep.tql;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

// Test TQL.g4 and TQLParser
// This is the basic of all other tests.
public class GrammarTest {

    @Test
    public void testParseKvStringequalAtom() throws IOException {


        String query = " sdf : \"akhd\"";
        ByteArrayInputStream input = new ByteArrayInputStream(query.getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.AtomContext ctx = parser.atom();
        return;
    }



    @Test
    public void testParseKvStringequalConj() throws IOException {
        String query = " sdf : \"akhd7t78t48g\"";
        ByteArrayInputStream input = new ByteArrayInputStream(query.getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.ConjuctionContext ctx = parser.conjuction();
        return;
    }


    @Test
    public void testParseKvStringequalDisj() throws IOException {
        String query = " sdf : \"akhd\"";
        ByteArrayInputStream input = new ByteArrayInputStream(query.getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.DisjuctionContext ctx = parser.disjuction();
        return;
    }


    @Test
    public void testParseKvStringequalQuery() throws IOException {


        String query = " sdf : \"akhd\"";
        ByteArrayInputStream input = new ByteArrayInputStream(query.getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.QueryContext ctx = parser.query();
        return;
    }



    @Test
    public void testParseNum() throws IOException {


        ByteArrayInputStream input = new ByteArrayInputStream("1136".getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.NumContext ctx = parser.num();
        TerminalNode terminal = (TerminalNode)ctx.getChild(0);
        assertEquals(TQLParser.NUM, terminal.getSymbol().getType());
        assertEquals("1136", terminal.getSymbol().getText());
        return;
    }
}

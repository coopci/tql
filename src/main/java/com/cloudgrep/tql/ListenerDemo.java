package com.cloudgrep.tql;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ListenerDemo {
    public static void main(String[] args) throws IOException {
        // ANTLRInputStream input = new ANTLRInputStream(System.in);


        ByteArrayInputStream input = new ByteArrayInputStream("sdf AND \"中文かな 한글\" AND 中文かな한글".getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        TQLParser.QueryContext rctx = parser.query();

        TQLBaseListener l = new TQLBaseListener() {

            /**
             * {@inheritDoc}
             *
             * <p>The default implementation does nothing.</p>
             */
            @Override public void enterQuery(@NotNull TQLParser.QueryContext ctx) {
                System.out.println("enterQuery:" + ctx.toString());



            }

            /**
             * {@inheritDoc}
             *
             * <p>The default implementation does nothing.</p>
             */
            @Override public void exitQuery(@NotNull TQLParser.QueryContext ctx) { }

            /**
             * {@inheritDoc}
             *
             * <p>The default implementation does nothing.</p>
             */
            @Override public void enterAtom(@NotNull TQLParser.AtomContext ctx) { }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation does nothing.</p>
             */
            @Override public void exitAtom(@NotNull TQLParser.AtomContext ctx) { }

            /**
             * {@inheritDoc}
             *
             * <p>The default implementation does nothing.</p>
             */
            @Override public void enterEveryRule(@NotNull ParserRuleContext ctx) { }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation does nothing.</p>
             */
            @Override public void exitEveryRule(@NotNull ParserRuleContext ctx) { }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation does nothing.</p>
             */
            @Override public void visitTerminal(@NotNull TerminalNode node) {
                System.out.println("visitTerminal:" + node.toString());
            }

        };
        rctx.enterRule(l);
        // ((TerminalNode)rctx.getChild(0)).getSymbol().getType()


    }
}

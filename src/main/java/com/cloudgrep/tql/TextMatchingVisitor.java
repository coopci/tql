package com.cloudgrep.tql;

import com.google.gson.Gson;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.Map;

public class TextMatchingVisitor extends TQLBaseVisitor<Boolean> {

    private final String text;

    public Map getParsed() {
        return parsed;
    }

    private Map parsed;

    public String getText() {
        return text;
    }

    public TextMatchingVisitor(String text) {
        this.text = text;
    }

//    @Override public Boolean visitGrep(@NotNull TQLParser.GrepContext ctx) {
//        return visitChildren(ctx);
//    }
//    @Override public Boolean visitStrequal(@NotNull TQLParser.StrequalContext ctx) {
//        return visitChildren(ctx);
//    }


    @Override public Boolean visitKvgrep (@NotNull TQLParser.KvgrepContext ctx) {

        TQLParser.FieldnameContext fnctx = ctx.fieldname();
        String fieldname = fnctx.getChild(0).getText();
        String fieldvalue = (String)this.getParsed().get(fieldname);
        String operand = ctx.getChild(2).getText();
        operand = operand.substring(1, operand.length()-1);
        return fieldvalue.contains(operand);
    }


    @Override public Boolean visitKvatom(@NotNull TQLParser.KvatomContext ctx) {
        return visit(ctx.getChild(0));
    }
    @Override public Boolean visitAtom(@NotNull TQLParser.AtomContext ctx) {
        return visit(ctx.getChild(0)); }


    @Override
    public Boolean visitQuery(@NotNull TQLParser.QueryContext ctx) {
        return visitChildren(ctx);
    }

    boolean isAND(ParseTree tree) {
        if (tree.getClass() == TerminalNodeImpl.class) {
            if (((TerminalNodeImpl) tree).symbol.getType() == TQLLexer.AND) {
                return true;
            }
        }
        return false;
    }
    @Override
    public Boolean visitOr(@NotNull TQLParser.OrContext ctx) {
        return false;
    }
    @Override
    public Boolean visitAnd(@NotNull TQLParser.AndContext ctx) {
        return true;
    }

    boolean isOR(ParseTree tree) {

        if (tree.getClass() == TerminalNodeImpl.class) {
            if (((TerminalNodeImpl) tree).symbol.getType() == TQLLexer.OR) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean visitConjuction(@NotNull TQLParser.ConjuctionContext ctx) {
        for (ParseTree child : ctx.children) {

            Boolean childRes = visit(child);
            if (!childRes) {
                return false;
            }

        }
        // all children returned true
        return true;
    }

    @Override
    public Boolean visitDisjuction(@NotNull TQLParser.DisjuctionContext ctx) {
        for (ParseTree child : ctx.children) {

            Boolean childRes = visit(child);
            if (childRes) {
                return true;
            }

        }
        // none of children returned true
        return false;
    }


    @Override public Boolean visitPipeline(@NotNull TQLParser.PipelineContext ctx) {
        // return visitChildren(ctx); // this is antlr default
        for(ParseTree c : ctx.children) {
            boolean childResult = c.accept(this);
            if (!childResult ){
                return false;
            }
        }
        return true;
    }


    @Override public Boolean visitPipe(@NotNull TQLParser.PipeContext ctx) {
        return true;
    }

    @Override
    public Boolean visitKw(@NotNull TQLParser.KwContext ctx) {
        // return visitChildren(ctx);

        String keyword = null;
        if (ctx.LITERAL() != null) {
            keyword = ctx.getText();
        }
        if (ctx.STRING() != null) {
            keyword = ctx.getText();
            keyword = keyword.substring(1, keyword.length() - 1);
        }


        return this.text.contains(keyword);
    }

    @Override
    public Boolean visitParsejson(@NotNull TQLParser.ParsejsonContext ctx) {
        Gson gson = new Gson();
        try {
            this.parsed = gson.fromJson(text, Map.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
}

package com.cloudgrep.tql;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class TextMatcher {
    private final TQLParser.QueryContext parseTree;
    public TextMatcher(String query) throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream(query.getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        this.parseTree = parser.query();
    }

    public boolean match(String t) {
        List<String> ret = new LinkedList<String>();

        TextMatchingVisitor visitor = new TextMatchingVisitor(t);
        return visitor.visit(parseTree);

    }
    public List<String> match(List<String> content) {
        List<String> ret = new LinkedList<String>();
        for(String t : content) {
            if(this.match(t)) {
                ret.add(t);
            }
        }
        return ret;

    }
}

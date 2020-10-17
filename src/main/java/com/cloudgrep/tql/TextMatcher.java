package com.cloudgrep.tql;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// 封装一下TextMatchingVisitor，提供一个方便的对外借口。
public class TextMatcher {

    public class Result {
        private boolean matched = false;
        // 如果有切字段，那么切好的字段放这里。
        private Map fields;

        public boolean isMatched() {
            return matched;
        }

        public void setMatched(boolean matched) {
            this.matched = matched;
        }

        public Map getFields() {
            return fields;
        }

        public void setFields(Map fields) {
            this.fields = fields;
        }

        Result(boolean matched, Map fields) {
            this.matched = matched;
            this.fields = fields;

        }
    }
    private final TQLParser.PipelineContext parseTree;

    /***
     *
     * @param query TQL语言的查询。
     */
    public TextMatcher(String query) throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream(query.getBytes());
        TQLLexer lexer = new TQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TQLParser parser = new TQLParser(tokens);
        this.parseTree = parser.pipeline();
    }

    public Result match(String t) {
        List<String> ret = new LinkedList<String>();

        TextMatchingVisitor visitor = new TextMatchingVisitor(t);
        boolean matched =  visitor.visit(parseTree);
        Result r = new Result(matched, visitor.getParsed());
        return r;

    }
}

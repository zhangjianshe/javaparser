package com.github.javaparser;

import java.util.Iterator;
import java.util.Optional;

import static com.github.javaparser.utils.Utils.assertNotNull;

/**
 * The range of tokens covered by this node.
 */
public class TokenRange implements Iterable<JavaToken> {
    public static final TokenRange INVALID = new TokenRange(JavaToken.INVALID, JavaToken.INVALID);

    private final JavaToken begin;
    private final JavaToken end;

    public TokenRange(JavaToken begin, JavaToken end) {
        this.begin = assertNotNull(begin);
        this.end = assertNotNull(end);
    }

    public JavaToken getBegin() {
        return begin;
    }

    public JavaToken getEnd() {
        return end;
    }

    public Range toRange() {
        return new Range(begin.getRange().begin, end.getRange().end);
    }

    public TokenRange withBegin(JavaToken begin) {
        return new TokenRange(assertNotNull(begin), end);
    }

    public TokenRange withEnd(JavaToken end) {
        return new TokenRange(begin, assertNotNull(end));
    }

    @Override
    public String toString() {
        JavaToken t = begin;
        StringBuilder result = new StringBuilder();
        while (true) {
            result.append(t.getText());
            if (t == end) {
                return result.toString();
            }
            Optional<JavaToken> next = t.getNextToken();
            if (next.isPresent()) {
                t = next.get();
            } else {
                return result.toString();
            }
        }
    }

    @Override
    public Iterator<JavaToken> iterator() {
        return new Iterator<JavaToken>() {
            private boolean hasNext = true;
            private JavaToken current = begin;

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public JavaToken next() {
                JavaToken retval = current;
                if (current == end) {
                    hasNext = false;
                }
                current = current.getNextToken().orElse(null);
                return retval;
            }
        };
    }
}

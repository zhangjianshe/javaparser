package com.github.javaparser.ast.validator;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.Statement;
import org.junit.Test;

import static com.github.javaparser.ParseStart.COMPILATION_UNIT;
import static com.github.javaparser.ParseStart.STATEMENT;
import static com.github.javaparser.Providers.provider;
import static com.github.javaparser.utils.TestUtils.assertNoProblems;
import static com.github.javaparser.utils.TestUtils.assertProblems;

public class Java1_4ValidatorTest {
    public static final JavaParser javaParser = new JavaParser(new ParserConfiguration().setValidator(new Java1_4Validator()));

    @Test
    public void noAssert() {
        ParseResult<Statement> result = javaParser.parse(STATEMENT, provider("assert a;"));
        assertNoProblems(result);
    }

    @Test
    public void noGenerics() {
        ParseResult<CompilationUnit> result = javaParser.parse(COMPILATION_UNIT, provider("class X<A>{List<String> b;}"));
        assertProblems(result,
                "(line 1,col 12) Generics are not supported.",
                "(line 1,col 1) Generics are not supported."
        );
    }
}

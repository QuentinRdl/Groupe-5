package fr.ufrst.m1info.pvm.group5.ast;

import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ASTDumpTest {
    @Test
    public void dumpToConsole() throws IOException {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/Complex.mjj");
        System.out.println(AST.dump());
    }
}

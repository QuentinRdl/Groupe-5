package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;

public class ASTInvalidDynamicTypeException extends RuntimeException {
    public ASTInvalidDynamicTypeException(int line, String expected, String actual, String operation) {
        super(String.format("Line %d : Invalid type, type %s given when %s is expected for %s", line, expected, actual, operation));
    }
}
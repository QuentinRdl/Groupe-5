package fr.ufrst.m1info.pvm.group5.ast;

public class InterpretationInvalidTypeException extends RuntimeException {
    public InterpretationInvalidTypeException(int line, String expected, String actual, String operation) {
        super(String.format("Invalid type, type %s given when %s is expected for %s (at line %d)", actual, expected, operation, line));
    }

    public InterpretationInvalidTypeException(String message, int line) {
        super("Line " + line + ": " + message);
    }
}
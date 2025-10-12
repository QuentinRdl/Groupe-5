package fr.ufrst.m1info.pvm.group5;

/**
 * Exception thrown when an operation is performed on a node, when it shouldn't be
 */
public class ASTInvalidOperationException extends RuntimeException {
    public ASTInvalidOperationException(String message) {
        super(message);
    }
}

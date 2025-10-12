package fr.ufrst.m1info.pvm.group5;

/**
 * Exception thrown when an error occured when building the tree (usually, when a node is null when it shouldn't be
 */
public class ASTBuildException extends RuntimeException {
    public ASTBuildException(String message) {
        super(message);
    }
}

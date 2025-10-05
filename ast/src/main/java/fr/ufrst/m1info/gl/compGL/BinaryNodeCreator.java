package fr.ufrst.m1info.gl.compGL;

import fr.ufrst.m1info.gl.compGL.Nodes.ASTNode;

/**
 * A factory interface to create and merge AST nodes with 2 children.
 * This interface is a necessity for binary operators, due to the way the grammar is made
 * @param <T> Type of node to create
 */
public interface BinaryNodeCreator<T extends ASTNode> {
    /**
     * Create a node from it's two children
     * @param left : left child of the node
     * @param right : right child of the node
     * @return new node
     */
    public T create(ASTNode left, ASTNode right);

    /**
     * Merge the current node with another one, to create a new node.
     * The current node will be placed as the left child of the new node, and the other as the right child.
     * @param other : Node to merge with the current one
     * @return new node created
     * @param <G> Type of node to create 
     */
    public <G extends ASTNode, BinaryNodeCreator> G merge(ASTNode other);
}

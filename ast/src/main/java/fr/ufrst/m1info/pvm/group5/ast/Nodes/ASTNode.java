package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidDynamicTypeException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;

import java.util.ArrayList;
import java.util.List;

public abstract class ASTNode {

    /**
     * Compile the node and its descendants
     * @return List of JaJaCodes compiled from it
     */
    public abstract List<String> compile(int address);

    /**
     * Interpret the node and it's descendants using a memory
     * @param m Memory used for the interpretation
     * @throws Exception throws an exception when an error occurs while trying to evaluate the node
     */
    public abstract void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException;

    /**
     * Dynamically checks the type of this node and returns its type.
     * @return the evaluated type of the node (e.g., int, bool, string, void, etc.)
     * @throws ASTInvalidDynamicTypeException if the type is invalid
     */
    public abstract String checkType(Memory m) throws ASTInvalidDynamicTypeException;

    /**
     * Get the children of the node within a list
     * @return List of the children of the node
     */
    protected abstract List<ASTNode>getChildren();

    /**
     * Create a version of the node in the JSON format
     * @return JSON description of the node
     */
    public String dump(){
        return dump(0);
    }

    /**
     * Prints non-children properties of the node
     * @param depth depth at which to print the properties of the node
     * @return properties of the node in the JSON format
     */
    protected abstract String dumpProperties(int depth);

    /**
     * Utility method. Add depth \t at the end of the stringBuilder
     * @param sb stringBuilder to add the \t to
     * @param depth number of \t to add
     */
    protected void addTabDepth(StringBuilder sb,int depth){
        sb.append("\t".repeat(Math.max(0, depth)));
    }

    /**
     * Creates a version of the node in the JSON format starting at a certain depth
     * @param depth depth to start at
     * @return node formatted in JSON
     */
    protected String dump(int depth){
        StringBuilder sb = new StringBuilder();
        addTabDepth(sb,depth);
        sb.append(this.getClass().getSimpleName());
        sb.append(" {\n");
        sb.append(dumpProperties(depth+1));
        List<ASTNode> children = this.getChildren();
        for(ASTNode node : children){
            sb.append(node.dump(depth+1));
        }
        addTabDepth(sb,depth);
        sb.append("}\n");
        return sb.toString();
    }

}

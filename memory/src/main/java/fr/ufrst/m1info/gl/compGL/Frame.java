package fr.ufrst.m1info.gl.compGL;
import java.util.*;

/**
 * Represents a singleexecution frame in the interpreter.
 * Each frame contains the different local variables, and a reference to the parent frame (if it exists)
 */

public class Frame {
    private Map<String, Object> localVariables; // Local variables on this scope
    private Frame parent; // Parent of the current frame

    private String funcName; // Name of the func // TODO : Keep it ?
    private int callLine; // Line of code that called the func // TODO : Keep it ?


    /**
     * Constructor for a Frame object
     * @param funcName Name of the func
     * @param callLine Line number where the frame was created
     * @param parent Parent frame
     */
    public Frame(String funcName, int callLine, Frame parent) {
       this.funcName = funcName;
       this.callLine = callLine;
       this.parent = parent;
       this.localVariables = new HashMap<>();
    }

}

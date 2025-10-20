package fr.ufrst.m1info.pvm.group5;

import fr.ufrst.m1info.pvm.group5.SymbolTable.DataType; // TODO : We should not have to import
import fr.ufrst.m1info.pvm.group5.SymbolTable.EntryKind;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Represents an entry of a variable in the stack, with name that contains the actual
 * name of the variable and scope the scope it has as a suffix
 */
public class Stack_Object{
    private final String name;
    private Object value;
    private final EntryKind entryKind;
    private final DataType dataType;
    private final int scope;

    /**
     * Constructor for objects other than Variables and Constants
     * @param name name of the var
     * @param value value of the var
     * @param scope scope of the var
     */
    public Stack_Object(String name, Object value, int scope, EntryKind kind) {
        // Reject generic construction for VARIABLE or CONSTANT
        // Those should use specific constructors
        if(kind == EntryKind.VARIABLE || kind == EntryKind.CONSTANT) {
            throw new InvalidStackObjectConstructionException(
                "Use the dedicated constructor for ENTRY kinds VARIABLE or CONSTANT: attempted to construct Stack_Object with type = " + type
            );
        }
        this.name = name;
        this.value = value;
        this.scope = scope;
        this.entryKind = kind;
        this.dataType = DataType.UNKNOWN;
    }

    /**
     * Specific constructor for Variables and Constants
     * @param name name of the var
     * @param value value of the var
     * @param scope scope of the var
     */
    public Stack_Object(String name, Object value, int scope, EntryKind entryKind, DataType dataType) {
        // Reject generic construction for VARIABLE or CONSTANT
        // Those should use specific constructors
        if(entryKind!= EntryKind.VARIABLE && entryKind != EntryKind.CONSTANT) {
            throw new InvalidStackObjectConstructionException(
                    "You should be using the generic constructor, and not " + entryKind + " !"
            );
        }
        this.name = name;
        this.value = value;
        this.scope = scope;
        this.entryKind = entryKind;
        this.dataType = dataType;
    }


    /**
     * Returns the name of the var
     * @return String name of the var
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the value of the var
     * @return Object value of the var
     */
    public Object getValue() {
        return value;
    }

    /**
     * Returns the scope of the var
     * @return int Scope of the var
     */
    public int getScope() {
        return scope;
    }

    public EntryKind getType() {
        return Entrytype;
    }

    /**
     * Sets the value of the var
     * @param value new value for the var
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Prints the var
     * @return String the var
     */
    @Override
    public String toString() {
        return name + "_" + scope + "=" + value;
    }
}

package fr.ufrst.m1info.gl.compGL.TableSymbole;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a simple symbol table without scope management.
 * This table stores entries (symbols) in a {@link HashMap}, where
 * the key is the symbol name and the value is a {@link SymbolTableEntry}.
 */
public class SymbolTable {

    /** The underlying map storing symbol names and their corresponding entries. */
    private final Map<String, SymbolTableEntry> table;

    /**
     * Constructs an empty symbol table.
     */
    public SymbolTable() {
        this.table = new HashMap<>();
    }

    /**
     * Adds a new entry to the symbol table.
     * If a symbol with the same name already exists, an exception is thrown.
     *
     * @param entry the symbol table entry to add
     * @throws IllegalArgumentException if the symbol name already exists in the table
     */
    public void addEntry(SymbolTableEntry entry) {
        String name = entry.getName();
        if (table.containsKey(name)) {
            throw new IllegalArgumentException("Symbol already declared: " + name);
        }
        table.put(name, entry);
    }

    /**
     * Searches for an entry in the table by its name.
     *
     * @param name the name of the symbol to search for
     * @return the corresponding {@link SymbolTableEntry}
     * @throws IllegalArgumentException if the symbol does not exist in the table
     */
    public SymbolTableEntry lookup(String name) {
        SymbolTableEntry entry = table.get(name);
        if (entry == null) {
            throw new IllegalArgumentException("Symbol not found: " + name);
        }
        return entry;
    }

    /**
     * Removes an entry from the symbol table by its name.
     *
     * @param name the name of the symbol to remove
     * @throws IllegalArgumentException if the symbol does not exist in the table
     */
    public void removeEntry(String name) {
        if (!table.containsKey(name)) {
            throw new IllegalArgumentException("Symbol not found: " + name);
        }
        table.remove(name);
    }

    /**
     * Prints all entries in the symbol table to the standard output.
     * Each entry is displayed using its {@link SymbolTableEntry#toString()} method.
     * If the table is empty, a message is displayed instead.
     */
    public void printTable() {
        if (table.isEmpty()) {
            System.out.println("Symbol table is empty.");
        } else {
            for (SymbolTableEntry entry : table.values()) {
                System.out.println(entry);
            }
        }
    }

    /**
     * Returns the number of entries currently in the symbol table.
     *
     * @return the number of entries in the table
     */
    public int size() {
        return table.size();
    }

    /**
     * Checks whether a symbol with the given name exists in the table.
     *
     * @param name the name of the symbol to check
     * @return {@code true} if the symbol exists, {@code false} otherwise
     */
    public boolean contains(String name) {
        return table.containsKey(name);
    }
}

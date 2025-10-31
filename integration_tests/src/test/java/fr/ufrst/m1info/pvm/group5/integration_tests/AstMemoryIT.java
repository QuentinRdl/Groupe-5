package fr.ufrst.m1info.pvm.group5.integration_tests;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.AbstractSyntaxTree;

import java.util.HashMap;
import java.util.Map;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class AstMemoryIT {
    private final String testPath = "../ast/";
    Map<String, Value> memoryStorage;
    Memory memory;

    @BeforeEach
    public void setup(){
        memoryStorage = new HashMap<>();
        memory = new Memory();
        memory.setPreserveAfterInterpret(true);
    }

    // Conversion of AbstractSyntaxTreeTest

    @Test
    @DisplayName("AST -> Memory Int Test | Evaluation - BasicOperations")
    public void BasicOperations() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("../ast/src/test/resources/BasicOperations.mjj");

        AST.interpret(memory);
        Object obj = memory.val("x");
        Value val = (Value) obj;
        assertEquals(8, val.valueInt);
    }

    @Test
    @DisplayName("AST -> Memory Int Test | Evaluation - OperationPrevalence")
    public void OperationPrevalence() throws Exception {

        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile(testPath + "src/test/resources/OperationPrevalence.mjj");
        AST.interpret(memory);

        Object obj = memory.val("x");
        Value val = (Value) obj;
        assertEquals(17, val.valueInt);

        obj = memory.val("y");
        val = (Value) obj;
        assertEquals(17, val.valueInt);

        obj = memory.val("z");
        val = (Value) obj;
        assertEquals(-1, val.valueInt);

        obj = memory.val("w");
        val = (Value) obj;
        assertTrue(val.valueBool);

        obj = memory.val("v");
        val = (Value) obj;
        assertTrue(val.valueBool);
    }

    @Test
    @DisplayName("AST -> Memory Int Test | Evaluation - Local Variables")
    public void LocalVariables() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile(testPath + "src/test/resources/LocalVariables.mjj");
        try {
            AST.interpret(memory);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }

        Object obj = memory.val("x");
        Value val = (Value) obj;
        assertEquals(9, val.valueInt);
    }

    @Test
    @DisplayName("AST -> Memory Int Test | Evaluation - Conditionnals")
    public void Conditionnals() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile(testPath + "src/test/resources/Conditionals.mjj");
        try {
            AST.interpret(memory);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }

        Object obj = memory.val("a");
        Value val = (Value) obj;
        assertEquals(5, val.valueInt);

        obj = memory.val("b");
        val = (Value) obj;
        assertEquals(6, val.valueInt);

        obj = memory.val("c");
        val = (Value) obj;
        assertEquals(5, val.valueInt);

        obj = memory.val("v");
        val = (Value) obj;
        assertEquals(6, val.valueInt);
    }

    @Test
    @DisplayName("AST -> Memory Int Test | Evaluation - Loops")
    public void Loops() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/Loops.mjj");
        try {
            AST.interpret(memory);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }

        Object obj = memory.val("x");
        Value val = (Value) obj;
        assertEquals(104, val.valueInt);
    }

    // End of conversion of AbstractSyntaxTreeTest
    // Conversion of AbstractSyntaxTreeTest
}

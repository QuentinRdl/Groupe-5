package fr.ufrst.m1info.pvm.group5.integration_tests;

import fr.ufrst.m1info.pvm.group5.ast.AbstractSyntaxTree;

import java.util.HashMap;
import java.util.Map;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class AstMemoryIT {

    Map<String, Value> memoryStorage;
    Memory memory;

    @BeforeEach
    public void setup(){
        memoryStorage = new HashMap<>();
        memory = new Memory();
    }


    @Test
    @DisplayName("Evaluation - BasicOperations")
    public void BasicOperations() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("../ast/src/test/resources/BasicOperations.mjj");

        AST.interpret(memory);
        // assertEquals(8, memoryStorage.get("x").valueInt);
        assertEquals(8, memory.val("x"));
    }
    /*

    @Test
    @DisplayName("Evaluation - OperationPrevalence")
    public void OperationPrevalence() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/OperationPrevalence.mjj");
        AST.interpret(memory);
        assertEquals(17, memoryStorage.get("x").valueInt);
        assertEquals(17, memoryStorage.get("y").valueInt);
        assertEquals(-1, memoryStorage.get("z").valueInt);
        assertEquals(true, memoryStorage.get("w").valueBool);
        assertEquals(true, memoryStorage.get("v").valueBool);
    }

    @Test
    @DisplayName("Evaluation - Undefined Variable / sum")
    public void UndefinedVariableSum() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromString("class C {int y = 10;main {x += y;}}");
        boolean success = false;
        try {
            AST.interpret(memory);
        }
        catch (ASTInvalidMemoryException e) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    @DisplayName("Evaluation - Undefined Variable / Inc")
    public void UndefinedVariableInc() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromString("class C {int y = 10;main {x++;}}");
        boolean success = false;
        try {
            AST.interpret(memory);
        }
        catch (ASTInvalidMemoryException e) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    @DisplayName("Evaluation - Undefined Variable / Evaluation")
    public void UndefinedVariableEval() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromString("class C {int y = 10;main {y = x;}}");
        boolean success = false;
        try {
            AST.interpret(memory);
        }
        catch (ASTInvalidMemoryException e) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    @DisplayName("Evaluation - Local Variables")
    public void LocalVariables() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/LocalVariables.mjj");
        try {
            AST.interpret(memory);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(9, memoryStorage.get("x").valueInt);
    }

    @Test
    @DisplayName("Evaluation - Conditionnals")
    public void Conditionnals() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/Conditionals.mjj");
        try {
            AST.interpret(memory);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(5, memoryStorage.get("a").valueInt);
        assertEquals(6, memoryStorage.get("b").valueInt);
        assertEquals(5, memoryStorage.get("c").valueInt);
        assertEquals(6, memoryStorage.get("v").valueInt);
    }

    @Test
    @DisplayName("Evaluation - Loops")
    public void Loops() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/Loops.mjj");
        try {
            AST.interpret(memory);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(104, memoryStorage.get("x").valueInt);
    }

     */
}

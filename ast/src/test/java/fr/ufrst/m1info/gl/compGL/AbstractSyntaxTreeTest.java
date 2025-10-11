package fr.ufrst.m1info.gl.compGL;

import fr.ufrst.m1info.gl.compGL.Memory.Memory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatchers;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AbstractSyntaxTreeTest {
    Map<String, Value> memoryStorage;
    Memory MemoryMock;

    @BeforeEach
    public void setup(){
        memoryStorage = new HashMap<>();
        MemoryMock = mock();
        doAnswer(invocation -> {
                    String arg =  invocation.getArgument(0);
                    Value value = invocation.getArgument(1);
                    memoryStorage.put(arg, value);
                    return null;
                }
        ).when(MemoryMock).affectValue(any(String.class), any(Object.class));

        doAnswer( invocation -> {
                String ident = invocation.getArgument(0);
                Value value = invocation.getArgument(1);
                memoryStorage.put(ident, value);
                return null;
            }
        ).when(MemoryMock).declVar(any(String.class), any(Value.class), any());

        doAnswer(invocation -> {
                String identifier =  invocation.getArgument(0);
                return memoryStorage.get(identifier);
            }
        ).when(MemoryMock).val(any(String.class));
    }

    @Test
    @DisplayName("Construction - Simple")
    public void SimpleTree(){
        String input = "class C {"
                     + "  main{}"
                     + "}";
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromString(input);
    }

    @Test
    @DisplayName("Construction - From file, simple")
    public void FromFile() throws IOException {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/Simple.mjj");
    }

    @Test
    @DisplayName("Construction - Complex")
    public void ComplexTree() throws IOException {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/Complex.mjj");
    }

    @Test
    @DisplayName("Evaluation - BasicOperations")
    public void BasicOperations() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/BasicOperations.mjj");
        AST.interpret(MemoryMock);
        assertEquals(8, memoryStorage.get("x").valueInt);
    }

    @Test
    @DisplayName("Evaluation - OperationPrevalence")
    public void OperationPrevalence() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/OperationPrevalence.mjj");
        AST.interpret(MemoryMock);
        assertEquals(17, memoryStorage.get("x").valueInt);
        assertEquals(17, memoryStorage.get("y").valueInt);
        assertEquals(-1, memoryStorage.get("z").valueInt);
        assertEquals(true, memoryStorage.get("w").valueBool);
        assertEquals(true, memoryStorage.get("v").valueBool);
    }

    @Test
    @DisplayName("Evaluation - Local Variables")
    public void LocalVariables() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/LocalVariables.mjj");
        AST.interpret(MemoryMock);
        assertEquals(9, memoryStorage.get("x").valueInt);
    }
}

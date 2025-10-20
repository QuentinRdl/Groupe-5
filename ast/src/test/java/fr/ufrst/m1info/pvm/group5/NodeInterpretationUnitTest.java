package fr.ufrst.m1info.pvm.group5;

import fr.ufrst.m1info.pvm.group5.Nodes.ASTNode;
import fr.ufrst.m1info.pvm.group5.Nodes.AddNode;
import fr.ufrst.m1info.pvm.group5.Nodes.NumberNode;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NodeInterpretationUnitTest {

    Map<String, Value> memoryStorage;
    @Mock
    Memory memory;

    @Before
    public void setup(){
        memoryStorage = new HashMap<>();
        memory = ASTMocks.createMemoryWithStorage(memoryStorage);
    }

    /**
     * ADD node
     */
    @Test
    public void AddNodeOperationTest(){
        NumberNode lop = mock();
        when(lop.eval(any(Memory.class))).thenReturn(new Value(5));
        NumberNode rop = mock();
        when(rop.eval(any(Memory.class))).thenReturn(new Value(10));
        AddNode tested = new AddNode(lop,rop);
        assertEquals(15, tested.eval(memory).valueInt);
    }

    @Test(expected = ASTBuildException.class)
    public void AddNodeMissingOperand(){
        NumberNode lop = mock();
        when(lop.eval(any(Memory.class))).thenReturn(new Value(5));
        AddNode tested = new AddNode(lop,null);
    }

    @Test(expected = ASTBuildException.class)
    public void AddNodeInvalidOperand(){
        ASTNode lop = mock();
        NumberNode rop = mock();
        when(rop.eval(any(Memory.class))).thenReturn(new Value(10));
        AddNode tested = new AddNode(lop,rop);
    }
}

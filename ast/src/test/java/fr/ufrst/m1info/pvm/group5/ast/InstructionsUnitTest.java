package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.ast.Instructions.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class InstructionsUnitTest {
    @Mock
    Memory memory;
    Stack<ASTMocks.Pair<String, Value>> storage;

    @BeforeEach
    public void setup(){
        storage = new Stack<>();
        memory = ASTMocks.createMemoryWithStack(storage);
    }

    //init
    @Test
    public void init(){
        Instruction initInstr = new InitInstruction();
        assertEquals(2,initInstr.execute(1,memory));
    }

    //if
    @Disabled
    @Test
    public void if_true(){
        Instruction pushInstr = new PushInstruction(new Value(true));
        Instruction ifInstr = new IfInstruction(5);
        pushInstr.execute(1,memory);
        assertEquals(5,ifInstr.execute(2,memory));
    }

    @Disabled
    @Test
    public void if_false(){
        Instruction pushInstr = new PushInstruction(new Value(false));
        Instruction ifInstr = new IfInstruction(5);
        pushInstr.execute(1,memory);
        assertEquals(3,ifInstr.execute(2,memory));
    }

    @Test
    public void if_empty_stack(){
        Instruction ifInstr = new IfInstruction(5);
        assertThrows(ASTInvalidMemoryException.class,() -> ifInstr.execute(2,memory));
    }

    //jcstop
    @Test
    public void jcstop(){
        Instruction jcstopInstr = new JcstopInstruction();
        assertEquals(-1,jcstopInstr.execute(1,memory));
    }

    //new
    @Disabled
    @Test
    public void new_var_int(){
        Instruction pushInstr = new PushInstruction(new Value(5));
        Instruction newInstr = new NewInstruction("x", DataType.INT, EntryKind.VARIABLE,0);
        pushInstr.execute(1,memory);
        assertEquals(3,newInstr.execute(2,memory));
        assertEquals(5,((Value) memory.val("x")).valueInt);
    }

    @Disabled
    @Test
    public void new_var_bool(){
        Instruction pushInstr = new PushInstruction(new Value(false));
        Instruction newInstr = new NewInstruction("x", DataType.BOOL, EntryKind.VARIABLE,0);
        pushInstr.execute(1,memory);
        assertEquals(3,newInstr.execute(2,memory));
        assertFalse(((Value) memory.val("x")).valueBool);
    }

    @Disabled
    @Test
    public void new_cst_int(){
        Instruction pushInstr = new PushInstruction(new Value(5));
        Instruction newInstr = new NewInstruction("x", DataType.INT, EntryKind.CONSTANT,0);
        pushInstr.execute(1,memory);
        assertEquals(3,newInstr.execute(2,memory));
        assertEquals(5,((Value) memory.val("x")).valueInt);
    }

    @Disabled
    @Test
    public void new_cst_bool(){
        Instruction pushInstr = new PushInstruction(new Value(false));
        Instruction newInstr = new NewInstruction("x", DataType.BOOL, EntryKind.CONSTANT,0);
        pushInstr.execute(1,memory);
        assertEquals(3,newInstr.execute(2,memory));
        assertFalse(((Value) memory.val("x")).valueBool);
    }

    //push
    @Test
    public void push_simple(){
        PushInstruction p = new PushInstruction(new Value(5));
        var res = p.execute(0,memory);
        assertEquals(1, res);
        assertEquals(5, storage.pop().second().valueInt);
    }

    @Test
    public void push_many(){
        PushInstruction p = new PushInstruction(new Value(5));
        PushInstruction z = new PushInstruction(new Value(0));
        p.execute(0,memory);
        p.execute(1,memory);
        var res = z.execute(2, memory);
        assertEquals(3, res);
        assertEquals(0, storage.pop().second().valueInt);
    }

}

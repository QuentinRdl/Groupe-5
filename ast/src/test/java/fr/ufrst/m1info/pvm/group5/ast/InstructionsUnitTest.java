package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.ast.Instructions.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
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

    //load
    @Test
    public void load_simple(){
        storage.add(new ASTMocks.Pair<>("test",new Value(5)));
        LoadInstruction l = new LoadInstruction("test");
        var res = l.execute(0,memory);
        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(5, top.second().valueInt);
        assertEquals(".", top.first());
        assertEquals(1, res);
    }

    @Test
    public void load_many(){
        storage.add(new ASTMocks.Pair<>("test1",new Value(1)));
        storage.add(new ASTMocks.Pair<>("test2",new Value(2)));
        storage.add(new ASTMocks.Pair<>("test3",new Value(3)));
        storage.add(new ASTMocks.Pair<>("test4",new Value(4)));

        LoadInstruction l = new LoadInstruction("test3");
        var res = l.execute(0,memory);
        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(3, top.second().valueInt);
        assertEquals(".", top.first());
        assertEquals(1, res);
    }

    //store
    @Test
    public void store_simple(){
        storage.add(new ASTMocks.Pair<>("test",new Value(1)));
        storage.add(new ASTMocks.Pair<>(".",new Value(5)));

        StoreInstruction s = new StoreInstruction("test");
        s.execute(0,memory);

        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(5, top.second().valueInt);
        assertEquals("test", top.first());
    }

    //add
    @Test
    public void add_simple(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(4)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(5)));

        AddInstruction a = new AddInstruction();
        a.execute(0,memory);

        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(9, top.second().valueInt);
        assertEquals(".", top.first());
    }


    @Test
    public void pop_simple() throws Exception{
        PushInstruction p1 = new PushInstruction(new Value(5));
        var res = p1.execute(0, memory);
        assertEquals(1, res);
        PopInstruction p2 = new PopInstruction();
        res = p2.execute(1, memory);
        assertEquals(2, res);
        assertTrue(storage.isEmpty());
    }

    //TODO: empty pop test

    @Test
    public void swap_simple_instruction() throws Exception {
        PushInstruction p1 = new PushInstruction(new Value(2));
        PushInstruction p2 = new PushInstruction(new Value(5));

        p1.execute(0, memory);
        p2.execute(1, memory);

        SwapInstruction swap = new SwapInstruction();
        var res = swap.execute(2, memory);

        assertEquals(3, res);
        assertEquals(2, storage.pop().second().valueInt);
        assertEquals(5, storage.pop().second().valueInt);
    }

    //TODO: empty swap test

    @Test
    public void goto_instruction() throws Exception {
        GotoInstruction i = new GotoInstruction(5);
        var res = i.execute(0, memory);
        assertEquals(5, res);
    }

    @Test
    public void nop_instruction() throws Exception {
        NopInstruction i = new NopInstruction();
        var res = i.execute(0, memory);
        assertEquals(1, res);
    }



}

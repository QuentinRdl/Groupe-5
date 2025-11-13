package fr.ufrst.m1info.pvm.group5.ast.Instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Stack_Object;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class ReturnInstruction extends Instruction {
    @Override
    public int execute(int address, Memory m) throws Exception {
        Stack_Object topObj = (Stack_Object) m.pop();
        Value top = Stack_Object.stackObjToValue(topObj);
        if(top.Type != ValueType.INT){
            throw new IllegalStateException("Return address must be int");
        }
        return top.valueInt;
    }
}

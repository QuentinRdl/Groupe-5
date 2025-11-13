package fr.ufrst.m1info.pvm.group5.ast.Instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Stack_Object;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class IfInstruction extends Instruction{
    int addressIf;

    public IfInstruction(int addressIf){
        this.addressIf = addressIf;
    }


    @Override
    public int execute(int address, Memory m) {
        boolean b;
        try {
            b = ((Value) m.pop()).valueBool;
        }catch (Exception e){
            throw new ASTInvalidMemoryException(e.getMessage());
        }
        if (b){
            return addressIf;
        }
        return address+1;
    }
}

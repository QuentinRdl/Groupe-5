package fr.ufrst.m1info.pvm.group5.ast.Instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;

public class StoreInstruction extends Instruction{
    String ident;

    public StoreInstruction(String ident){
        this.ident = ident;
    }

    @Override
    public int execute(int address, Memory m) {
        m.affectValue(this.ident, m.pop());
        return address+1;
    }
}

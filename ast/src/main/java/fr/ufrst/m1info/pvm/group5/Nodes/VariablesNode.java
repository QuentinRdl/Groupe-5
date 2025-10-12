package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.ASTBuildException;
import fr.ufrst.m1info.pvm.group5.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.Memory.Memory;
import fr.ufrst.m1info.pvm.group5.WithradawableNode;

import java.util.ArrayList;
import java.util.List;

public class VariablesNode extends ASTNode {
    ASTNode variable;
    ASTNode variables;

    public VariablesNode(ASTNode variable, ASTNode variables){
        this.variable=variable;
        this.variables=variables;
        if(this.variable==null){
            throw new ASTBuildException("Invalid variable declaration");
        }
        if(!(this.variable instanceof WithradawableNode)){
            throw new ASTBuildException("Variable declarations must be withdrawable");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.addAll(variable.compile(address));
        if (variables != null){
            jajacodes.addAll(variables.compile(address + jajacodes.size()));
        }
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        variable.interpret(m);
        if(variables != null) {
            variables.interpret(m);
        }
    }
}

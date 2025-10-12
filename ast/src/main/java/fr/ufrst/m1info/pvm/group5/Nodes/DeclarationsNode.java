package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.ASTBuildException;
import fr.ufrst.m1info.pvm.group5.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.Memory.Memory;
import fr.ufrst.m1info.pvm.group5.WithradawableNode;

import java.util.ArrayList;
import java.util.List;

public class DeclarationsNode extends ASTNode{
    ASTNode declaration;
    ASTNode declarations;

    public DeclarationsNode(ASTNode declaration, ASTNode declarations){
        this.declaration=declaration;
        this.declarations=declarations;
        if(declaration == null){
            throw new ASTBuildException("Invalid declaration");
        }
        if(!(declaration instanceof WithradawableNode)){
            throw new ASTBuildException("Declarations must be withdrawable");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.addAll(declaration.compile(address));
        if (declarations != null) {
            jajacodes.addAll(declarations.compile(address + jajacodes.size()));
        }
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        declaration.interpret(m);
        if(declarations != null)
            declarations.interpret(m);
    }
}

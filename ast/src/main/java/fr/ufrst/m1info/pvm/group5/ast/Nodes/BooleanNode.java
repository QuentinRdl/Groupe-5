package fr.ufrst.m1info.pvm.group5.ast.Nodes;

<<<<<<< HEAD:ast/src/main/java/fr/ufrst/m1info/pvm/group5/Nodes/BooleanNode.java
=======
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.ast.EvaluableNode;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.ast.Value;
>>>>>>> e5e84057924c76a4518dbace8f2796e7c8280012:ast/src/main/java/fr/ufrst/m1info/pvm/group5/ast/Nodes/BooleanNode.java

import java.util.ArrayList;
import java.util.List;

public class BooleanNode extends ASTNode implements EvaluableNode {
    boolean bool;

    public BooleanNode(boolean bool){
        this.bool = bool;
    }
    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        if (this.bool){
            jajacodes.add("push(jcvrai)");
        } else {
            jajacodes.add("push(jcvrai)");
        }
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException {
        throw new ASTInvalidOperationException("Cannot interpret Boolean node");
    }

    @Override
    public String checkType() throws ASTInvalidDynamicTypeException {
        return "bool";
    }
    @Override
    public Value eval(Memory m) {
        return new Value(this.bool);
    }
}

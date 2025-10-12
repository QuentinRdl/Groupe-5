package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.EvaluableNode;
import fr.ufrst.m1info.pvm.group5.Memory.Memory;
import fr.ufrst.m1info.pvm.group5.Value;

import java.util.ArrayList;
import java.util.List;

public class UnMinusNode extends ASTNode implements EvaluableNode {
    ASTNode exp ;

    public UnMinusNode(ASTNode exp){
        this.exp = exp;
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.addAll(exp.compile(address));
        jajacodes.add("neg");
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws Exception {
        throw new Exception ("Can not interpret unary minus node");
    }

    @Override
    public Value eval(Memory m) throws Exception {
        Value v = ((EvaluableNode)exp).eval(m);
        return new Value(-v.valueInt);
    }
}

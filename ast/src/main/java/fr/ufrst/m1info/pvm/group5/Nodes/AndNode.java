package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.Value;

public class AndNode extends BinaryOperator{

    public AndNode(ASTNode left, ASTNode right) {
        super(left, right);
    }

    @Override
    protected String getCompileName() {
        return "and";
    }

    @Override
    protected Value mainOperation(Value leftOperand, Value rightOperand) throws Exception{
        return new Value(leftOperand.valueBool && rightOperand.valueBool);
    }
}

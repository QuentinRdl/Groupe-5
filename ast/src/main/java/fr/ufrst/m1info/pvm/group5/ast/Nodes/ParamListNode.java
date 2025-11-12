package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

import java.util.ArrayList;
import java.util.List;

public class ParamListNode extends ASTNode {

    private final ASTNode param;
    private final ASTNode next;

    public ParamListNode(ASTNode param, ASTNode next) {
        if (param == null) {
            throw new ASTBuildException("ParamListNode cannot have a null parameter");
        }
        this.param = param;
        this.next = next;
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<>();
        jajacodes.addAll(param.compile(address));
        if (next != null) {
            jajacodes.addAll(next.compile(address + jajacodes.size()));
        }

        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {

        if (param instanceof ParamNode p) {
            ValueType vt = p.type.valueType;
            m.declVar(p.ident.identifier, new Value(), ValueType.toDataType(vt));
        }
        if (next != null) {
            next.interpret(m);
        }
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        if (param instanceof ParamNode p) {
            if (p.type.valueType == ValueType.VOID) {
                throw new ASTInvalidDynamicTypeException(
                        "Parameter " + p.ident.identifier + " cannot have type void"
                );
            }
        }
        if (next != null) {
            next.checkType(m);
        }

        return "void";
    }

    @Override
    protected List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        children.add(param);
        if (next != null)
            children.add(next);
        return children;
    }
}

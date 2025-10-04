package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.Memory;

import java.util.ArrayList;
import java.util.List;

public class InstructionsNode extends ASTNode{
    ASTNode instruction;
    ASTNode otherInstructions;

    public InstructionsNode(ASTNode instruction, ASTNode otherInstructions) {
        this.instruction = instruction;
        this.otherInstructions = otherInstructions;
    }

    @Override
    public List<String> compile() {
        List<String> JJCodes = new ArrayList<>();
        JJCodes.addAll(instruction.compile());
        JJCodes.addAll(otherInstructions.compile());
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws Exception {
        instruction.interpret(m);
        otherInstructions.interpret(m);
    }
}

package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.ast.instructions.Instruction;
import fr.ufrst.m1info.pvm.group5.ast.nodes.ASTNode;

import java.lang.reflect.Executable;

public class MemoryCallUtil {
    public static void safeCall(Runnable call, Instruction caller){
        try{
            call.run();
        }
        catch (Exception e){
            throw ASTInvalidMemoryException.InvalidMemoryOperation(caller.getLine(), e.getMessage());
        }
    }

    public static void safeCall(Runnable call, ASTNode caller){
        try{
            call.run();
        }
        catch (Exception e){
            throw ASTInvalidMemoryException.InvalidMemoryOperation(caller.getLine(), e.getMessage());
        }
    }
}

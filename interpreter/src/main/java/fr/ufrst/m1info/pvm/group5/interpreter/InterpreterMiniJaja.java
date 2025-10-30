package fr.ufrst.m1info.pvm.group5.interpreter;


import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.ast.AbstractSyntaxTree;
import fr.ufrst.m1info.pvm.group5.memory.Writer;

public class InterpreterMiniJaja implements Interpreter{
    Writer output;

    protected InterpreterMiniJaja(){
        output = null;
    }

    public InterpreterMiniJaja(Writer output) {
        this.output = output;
    }

    /**
     * Interpret the given code
     *
     * @param code the code we want to interpret
     * @return the error message
     */
    @Override
    public String interpretCode(String code) {
        System.out.println("[DEBUG] >>> interpretCode() appelé avec code = ");
        System.out.println(code);
        System.out.println("[DEBUG] Writer dans InterpreterMiniJaja : " + output);
        Memory mem = new Memory(output);
        String errMessage= null;
        try{
            AbstractSyntaxTree ast = AbstractSyntaxTree.fromString(code);
            ast.interpret(mem);
        } catch (Exception e) {
            errMessage=e.getClass()+" : "+e.getMessage();
            output.writeLineAsync(errMessage);
        }
        System.out.println("[DEBUG] <<< interpretCode() terminé");
        return errMessage;
    }

    /**
     * Interpret the given file
     *
     * @param path the path of the file we want to interpret
     * @return the error message
     */
    @Override
    public String interpretFile(String path)  {
        Memory mem = new Memory(output);
        String errMessage= null;
        try{
            AbstractSyntaxTree ast = AbstractSyntaxTree.fromFile(path);
            ast.interpret(mem);
        } catch (Exception e) {
            errMessage=e.getClass()+" : "+e.getMessage();
            output.writeLineAsync(errMessage);
        }
        return errMessage;
    }
}

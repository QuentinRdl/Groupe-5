package fr.ufrst.m1info.pvm.group5.interpreter;


import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.ast.AbstractSyntaxTree;
import fr.ufrst.m1info.pvm.group5.memory.Writer;

public class InterpreterMiniJaja implements Interpreter{
    Writer output;
    private Memory mem;

    protected InterpreterMiniJaja(){
        output = new Writer();
        mem = new Memory(output);
    }

    public InterpreterMiniJaja(Writer output) {
        this.output = output;
        mem = new Memory(this.output);
    }

    /**
     * Interpret the given code
     *
     * @param code the code we want to interpret
     * @return the error message
     */
    @Override
    public String interpretCode(String code) {
        String errMessage= null;
        try{
            AbstractSyntaxTree ast = AbstractSyntaxTree.fromString(code);
            ast.interpret(mem);
        } catch (Exception e) {
            errMessage=e.getClass()+" : "+e.getMessage();
        }
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
        String errMessage= null;
        try{
            AbstractSyntaxTree ast = AbstractSyntaxTree.fromFile(path);
            ast.interpret(mem);
        } catch (Exception e) {
            errMessage=e.getClass()+" : "+e.getMessage();
        }
        return errMessage;
    }

    public Memory getMemory(){
        return this.mem;
    }
}

package fr.ufrst.m1info.pvm.group5;


public class InterpreterMiniJaja implements Interpreter{
    /**
     * Interpret the given code
     *
     * @param code the code we want to interpret
     */
    @Override
    public void interpretCode(String code) throws Exception {
        Memory mem = new Memory();
        AbstractSyntaxTree ast = AbstractSyntaxTree.fromString(code);
        ast.interpret();
    }

    /**
     * Interpret the given file
     *
     * @param path the path of the file we want to interpret
     */
    @Override
    public void interpretFile(String path) throws Exception {
        Memory mem = new Memory();
        AbstractSyntaxTree ast = AbstractSyntaxTree.fromFile(path);
        ast.interpret();
    }
}

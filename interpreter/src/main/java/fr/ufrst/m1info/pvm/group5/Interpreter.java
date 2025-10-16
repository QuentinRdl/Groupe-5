package fr.ufrst.m1info.pvm.group5;

public interface Interpreter {
    /**
     * Interpret the given code
     *
     * @param code the code we want to interpret
     */
    void interpretCode(String code) throws Exception;

    /**
     * Interpret the given file
     *
     * @param path the path of the file we want to interpret
     */
    void interpretFile(String path) throws Exception;
}

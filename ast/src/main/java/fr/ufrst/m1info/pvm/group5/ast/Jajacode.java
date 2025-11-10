package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.JajaCodeLexer;
import fr.ufrst.m1info.pvm.group5.JajaCodeParser;
import fr.ufrst.m1info.pvm.group5.ast.Instructions.Instruction;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.io.IOException;
import java.util.List;

public class Jajacode {


    private static List<Instruction> fromCharStream(CharStream in) throws ParseCancellationException {
        JajaCodeLexer lexer = new JajaCodeLexer(in);
        lexer.removeErrorListeners();
        lexer.addErrorListener(ExceptionErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JajaCodeParser parser = new JajaCodeParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(ExceptionErrorListener.INSTANCE);

        return parser.eval().instrs;
    }

    public static List<Instruction> fromString(String input){
        CharStream in = CharStreams.fromString(input);
        return fromCharStream(in);
    }


    public static List<Instruction> fromFile(String filePath) throws IOException {
        CharStream in = CharStreams.fromFileName(filePath);
        return fromCharStream(in);
    }
}

package fr.ufrst.m1info.gl.compGL;

import java.util.ArrayList;
import java.util.List;

public class JaJaCodes {
    private List<String> instrs;

    JaJaCodes(){
        instrs = new ArrayList<>();
    }

    void Add(List<String> instrs){
        this.instrs.addAll(instrs);
    }

    void Add(String instr){
        this.instrs.add(instr);
    }

    void List<String> get(){

    }
}

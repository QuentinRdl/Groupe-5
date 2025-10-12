package fr.ufrst.m1info.pvm.group5;

import fr.ufrst.m1info.pvm.group5.Memory.Memory;

public interface EvaluableNode {
    public Value eval(Memory m) throws Exception;
}

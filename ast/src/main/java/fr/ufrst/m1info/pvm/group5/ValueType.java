package fr.ufrst.m1info.pvm.group5;

import fr.ufrst.m1info.pvm.group5.Memory.DataType;

public enum ValueType {
    INT,
    BOOL,
    VOID;

    public static DataType toDataType(ValueType vt){
        switch(vt){
            case INT:
                return DataType.INT;
            case BOOL:
                return DataType.BOOL;
            case VOID:
                return DataType.VOID;
        }
        return DataType.UNKNOWN;
    }
}

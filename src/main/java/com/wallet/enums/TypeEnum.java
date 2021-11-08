package com.wallet.enums;

public enum TypeEnum {

    EN("ENTRADA"),
    SD("SAIDA");

    private final String value;

    TypeEnum(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public static TypeEnum valuesFrom(String value){
        for (TypeEnum t: values()){
            if(value.equalsIgnoreCase(t.getValue())){
                return t;
            }
        }
        return null;
    }
}

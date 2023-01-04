package org.example.Calculator;

public class CalcException extends Exception{

    private static final long serialVersionUID = 1L;
    private String errStr;  //  Описание ошибки

    public CalcException(String errStr) {
        super();
        this.errStr = errStr;
    }

    public String toString(){
        return this.errStr;
    }

    static void handleErr(int nOEXP2) throws CalcException{

        String[] err  = {
                "Syntax error",
                "Unbalanced Parentheses",
                "No Expression Present",
                "Division by zero"
        };
        throw new CalcException(err[nOEXP2]);
    }
}
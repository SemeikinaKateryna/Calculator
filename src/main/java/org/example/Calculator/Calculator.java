package org.example.Calculator;

import java.text.DecimalFormat;

/**
 *  Класс Calculator, основной класс где просходит вычесление выражения
 */
public class Calculator {

    /** Ошибка */
    final int NONE = 0;       
    // Разделители(+-*/^=, ")", "(" )
    final int DELIMITER = 1;     
    /** переменная */
    final int VARIABLE = 2;    
    /** Число */
    final int NUMBER = 3;      
 
    /** константы синтаксических ошибок */
    
    /** Синтаксическая ошибка (10 + 5 6 / 1) */  
    final int SYNTAXERROR = 0;
    /** Несовпадение количества открытых и закрытых скобок */
    final int UNBALPARENS = 1;  
    /** Отсутствует выражение при запуске анализатора */
    final int NOEXP = 2; 
    /** Ошибка деления на ноль */
    final int DIVBYZERO = 3;
    /** лексема, определяющая конец выражения */
    final String EOF = "\0";
    /** Ссылка на строку с выражением */
    private String exp; 
    /** Текущий индекс в выражении */
    private int explds;   
    /** Сохранение текущей лексемы */
    private String token;  
    /**  Сохранение типа лексемы */
    private int tokType;  

    /** Получить следующую лексему */
    private void getToken(){
        tokType = NONE;
        token = "";

        /**  Проверка на конец выражения*/
        if(explds == exp.length()){
            token = EOF;
            return;
        }
         /** проверка на пробелы, если есть пробел - игнорируем его. */
        while(explds < exp.length() && Character.isWhitespace(exp.charAt(explds))){
             explds++;
        }
        /**  Проверка на конец выражения*/
        if(explds == exp.length()){
            token = EOF;
            return;
        }
        /**  Проверка на допустипимые символы*/
        if(isValidChartres(exp.charAt(explds))){
            token += exp.charAt(explds);
            explds++;
            tokType = DELIMITER;
        }
        else if(Character.isLetter(exp.charAt(explds))){
            while(!isValidChartres(exp.charAt(explds))){
                token += exp.charAt(explds);
                explds++;
                if(explds >= exp.length()){
                    break;
                }
            }
            tokType = VARIABLE;
        }
        else if (Character.isDigit(exp.charAt(explds))){
            while(!isValidChartres(exp.charAt(explds))){
                token += exp.charAt(explds);
                explds++;
                if(explds >= exp.length()){
                    break;
                }
            }
            tokType = NUMBER;
        }
        else {
            token = EOF;
            return;
        }
    }

    private boolean isValidChartres(char charAt) {
        if((" +-/*%^=()".indexOf(charAt)) != -1)
            return true;
        return false;
    }

    /** Точка входа анализатора */
    public double analizator(String expstr) throws CalcException {
        double result;
        exp = expstr;
        explds = 0;
        getToken();
        if(token.equals(EOF))
            CalcException.handleErr(NOEXP);

        result = plusOrMinus();
        
        if(!token.equals(EOF)){
            CalcException.handleErr(SYNTAXERROR);
        }
        return result;
    }
    
    private double plusOrMinus() throws CalcException {
        char symbol;
        double result;
        double partialResult;

        result = multiplyDivide();
        while((symbol = token.charAt(0)) == '+' || symbol == '-'){
            getToken();
            partialResult = multiplyDivide();
            switch(symbol){
                case '-':
                    result -= partialResult;{
                    break;
                }
                case '+':
                    result += partialResult;{
                    break;
                }
            }
        }
        return result;
    }

    private double multiplyDivide() throws CalcException {
        char symbol;
        double result;
        double partialResult;

        result = degree();
        while((symbol = token.charAt(0)) == '*' || symbol == '/' | symbol == '%'){
            getToken();
            partialResult = degree();
            switch(symbol){
                case '*':
                    result *= partialResult;
                    break;
                case '/':
                    if(partialResult == 0.0)
                        CalcException.handleErr(DIVBYZERO);
                    result /= partialResult;
                    break;
                case '%':
                    if(partialResult == 0.0)
                        CalcException.handleErr(DIVBYZERO);
                    result %= partialResult;
                    break;
            }
        }
        return result;
    }

    private double degree() throws CalcException {
        double result;
        double partialResult;
        double ex;

        result = unarPlusOrMinus();
        if(token.equals("^")){
            getToken();
            partialResult = degree();
            ex = result;
            if(partialResult == 0.0){
                result = 1.0;
            }else{
                for(int i = (int)partialResult - 1; i >  0; i--){
                    result *= ex;
                }
            }
        }
        return result;
    }

    private double unarPlusOrMinus() throws CalcException {
        double result;

        String symbol;
        symbol = " ";

        if((tokType == DELIMITER) && token.equals("+") || token.equals("-")){
            symbol = token;
            getToken();
        }
        result = parentheses();
        if(symbol.equals("-"))
            result =  -result;
        return result;
    }

    private double parentheses() throws CalcException {
        double result;
        if(token.equals("(")){
            getToken();
            result = plusOrMinus();
            if(!token.equals(")"))
                CalcException.handleErr(UNBALPARENS);
            getToken();
        }
        else{
            result = atom();
        }
        return result;
    }

    /** Функция, которая выводит конечный результат вычислений */
    private double atom() throws CalcException {
        double result = 0.0;
        switch(tokType){
            case NUMBER:
                try{
                    result = Double.parseDouble(token);
                }
                catch(NumberFormatException exc){
                    CalcException.handleErr(SYNTAXERROR);
                }
                getToken();

                break;
            default:
                CalcException.handleErr(SYNTAXERROR);
                break;
        }
        return result;
    }
    

}
package org.example.input;

import org.example.calculator.CalcException;
import org.example.Main;
import org.example.calculator.Calculator;
import org.example.dao.MySQLDAO;

import java.io.IOException;
import java.util.Scanner;

/**
 *  Класс Input, который содержит вспомагательные методы для ввода текста и вызова функций из Main
 */

public class Input {
    public static String consoleScanner(String message) {
        Scanner console = new Scanner(System.in);
        System.out.print(message);
        return console.nextLine();
    }

    public static void methodStart() throws IOException, CalcException {
        Calculator calculator = new Calculator();
        String expression = consoleScanner("Введите арифметическое выражение : ");
        double result = calculator.analizator(expression);
        System.out.println(result);
        MySQLDAO.addExpression(expression,result);
        Main.run();
    }
    public static void methodGetAllExpression() throws IOException, CalcException {
        MySQLDAO.getAllExpression();
        Main.run();
    }
    public static void methodGetExpressionByResult() throws IOException, CalcException {
        double res = Double.parseDouble(Input.consoleScanner("Введите искомый результат: "));
        MySQLDAO.getExpressionByResult(res);
        Main.run();
    }
    public static void methodGetExpressionByMoreResult() throws IOException, CalcException {
        double res = Double.parseDouble(Input.consoleScanner("Введите искомый результат: "));
        MySQLDAO.getExpressionByMoreResult(res);
        Main.run();
    }
    public static void methodGetExpressionByLessResult() throws IOException, CalcException {
        double res = Double.parseDouble(Input.consoleScanner("Введите искомый результат: "));
        MySQLDAO.getExpressionByLessResult(res);
        Main.run();
    }

    public static void methodChangeExpressionByID() throws IOException, CalcException {
        int id = Integer.parseInt(Input.consoleScanner
                ("Введите ID выражения, которое нужно изменить: "));
        String str = Input.consoleScanner("Введите новое выражение: ");
        MySQLDAO.changeExpression(str,id);
        Main.run();
    }
}

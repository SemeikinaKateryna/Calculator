package org.example;

import org.example.calculator.CalcException;
import org.example.input.Input;

import java.io.IOException;

/** Класс Main - точка входа программы
 * Был разработан калькулятор, который вычесляет
 * на основе обратной польской нотации,
 * может выполнять (/) (*) (-) (+) (^) (%)
 * Работает как с целыми числами так и с дробными
 */
public class Main {
    public static void main(String[] args) throws IOException, CalcException {
        Main.run();
    }
    public static void run() throws IOException, CalcException {
        System.out.println(
                "Введите цифру действия \n"+
                        "1) Ввести арифметическое выражение\n" +
                        "2) Вывести истории выражений \n" +
                        "3) Найти выражение (по результату)\n" +
                        "4) Редактировать выражение\n" +
                        "5) Закрыть (exit)\n");

        String choice = Input.consoleScanner("Введите : ");
        switch(choice) {
            case "1":
                Input.methodStart();
                break;
            case "2":
                Input.methodGetAllExpression();
                break;
            case "3":
                Input.methodGetExpressionByID();
                break;
            case "4":
                Input.methodChangeExpressionByID();
                break;
            case "5":
            case "exit":
                break;
            default:
                System.out.println("Неизвестная команда");
                Main.run();
                break;
        }
    }

}
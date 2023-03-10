package org.example.dao;

import org.example.calculator.CalcException;
import org.example.calculator.Calculator;

import java.sql.*;
import java.text.DecimalFormat;

/**
 *  Класс MySQLDAO, который обеспечивает подключение к БД и содержит функции работы с БД
 */

public class MySQLDAO{
    private static final String URL = "jdbc:mysql://localhost/calculator?";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "Katya";
    static String NAME_OF_TABLE_EXPRESSION = "expression";

    /** Все SQL-запросы, которые будут в дальнейшем использоваться*/
    public static String GET_ALL_EXPRESSION = "SELECT * FROM " + NAME_OF_TABLE_EXPRESSION;
    public static String INSERT_DATA_INTO_EXPRESSION = "INSERT INTO " + NAME_OF_TABLE_EXPRESSION
            + "(expression,result) VALUES(?, ?) ";
    public static String SELECT_EXPRESSION_BY_RESULT = "SELECT * FROM " + NAME_OF_TABLE_EXPRESSION
            + " WHERE result = ?";
    public static String SELECT_EXPRESSION_BY_MORE_RESULT = "SELECT * FROM " + NAME_OF_TABLE_EXPRESSION
            + " WHERE result > ?";
    public static String SELECT_EXPRESSION_BY_LESS_RESULT = "SELECT * FROM " + NAME_OF_TABLE_EXPRESSION
            + " WHERE result < ?";
    public static String UPDATE_EXPRESSION_RESULT = "UPDATE " + NAME_OF_TABLE_EXPRESSION +
            " SET expression = ?, result = ? WHERE idExpression = ?";

    public MySQLDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** Получение всех обьектов*/
    static public void getAllExpression() {
        try (Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(GET_ALL_EXPRESSION);
            while (rs.next()) {
                System.out.println(" idExpression : " + rs.getInt("idExpression") +
                        " expression : " + rs.getString("expression") +
                        " result : " + rs.getDouble("result"));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Добавление нового выражения в таблицу*/
    public static void addExpression(String expression, double result){
        try(Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD)){
            PreparedStatement ps = conn.prepareStatement(INSERT_DATA_INTO_EXPRESSION);
            ps.setString(1, expression);
            DecimalFormat decimalFormat = new DecimalFormat( "#.###" );
            result = Double.parseDouble(decimalFormat.format(result));
            ps.setDouble(2, result);
            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /** Получение обьекта по заданному параметру (=результату/>результата/<результата)*/
    static public void getExpressionByResult(double result){
        try (Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(SELECT_EXPRESSION_BY_RESULT);
            ps.setDouble(1, result);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(" idExpression : " + rs.getInt("idExpression") +
                        " expression : " + rs.getString("expression") +
                        " result : " + rs.getDouble("result"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public void getExpressionByMoreResult(double result){
        try (Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(SELECT_EXPRESSION_BY_MORE_RESULT);
            ps.setDouble(1, result);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(" idExpression : " + rs.getInt("idExpression") +
                        " expression : " + rs.getString("expression") +
                        " result : " + rs.getDouble("result"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static public void getExpressionByLessResult(double result){
        try (Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(SELECT_EXPRESSION_BY_LESS_RESULT);
            ps.setDouble(1, result);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(" idExpression : " + rs.getInt("idExpression") +
                        " expression : " + rs.getString("expression") +
                        " result : " + rs.getDouble("result"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /** Редактирование выражения по айди*/
    static public void changeExpression(String newExpression, int id){
        try(Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD)){
            PreparedStatement ps = conn.prepareStatement(UPDATE_EXPRESSION_RESULT);
            ps.setString(1,newExpression );
            Calculator calculator = new Calculator();
            double result = calculator.analizator(newExpression);
            ps.setDouble(2,result);
            ps.setInt(3, id);
            ps.executeUpdate();
            ps.close();
        } catch (CalcException e) {
            throw new RuntimeException(e);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

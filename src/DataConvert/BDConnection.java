package DataConvert;

import java.sql.*;
import java.util.ArrayList;

public class BDConnection {
    private static final String password = "Baal112Hammon";
    private static final String user = "root";
    private static final String url = "jdbc:mysql://localhost:3306/test";

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public void getConnection () {
        System.out.println("Метод getConnection: Создаем соединение с бд");

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        }
        catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
        }
    }

    public void InsertRequestForTestTable(VSData vsObject){
        System.out.println("Метод InsertRequestForTestTable: Отправляем INSERT запрос в таблицу test в бд с параметрами: \n" +
                "'" +vsObject.getSourse() + "', '" + vsObject.getTarget() + "', '" + vsObject.getWeight() + "', '" + VSData.getOriginal_therm() +"'");

        try {
            statement.executeUpdate("USE test");

            String query = "INSERT INTO result(source, target, weight, original_term) VALUES(' \n" +
                        "" + vsObject.getSourse() + "', '" + vsObject.getTarget() + "', '" + vsObject.getWeight() + "', '" + VSData.getOriginal_therm() + "');";

            statement.executeUpdate(query);
            }
        catch (SQLException e) {
            System.out.println("SQLException:" + e.getMessage());
        }
    }

    public void closeConnection (){
        System.out.println("Метод closeConnection: Закрываем соединение с бд \n" + "---------------------------------------------------");

        try{
            if(statement!= null) {
                statement.close();
            }
            if(resultSet!= null) {
                resultSet.close();
            }
            if(connection!= null) {
                connection.close();
            }
        }
        catch (SQLException e){
            System.out.println("SQLException:" + e.getMessage());
        }
    }

    public ArrayList<String> getTarget(int id) {
        System.out.println("Метод getTarget: Получаем массив вторичных заросов из бд");

        ArrayList<String> targetList = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.getResultSet();

            String query = "SELECT target FROM result WHERE id >= "+ (id);

            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                String target = resultSet.getString("target");
                targetList.add(target);
            }
        }
        catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        }

        return targetList;
    }
}

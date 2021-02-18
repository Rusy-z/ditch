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

    public static void getConnection () {
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            System.out.println("Connection exist................................................");
        }
        catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
        }
    }

    public static void InsertRequestForTastTable(ArrayList<VSData> arr){
        metka:try {
            try {
                statement.executeUpdate("USE test");
            }
            catch (SQLException e) {
                System.out.println("SQLException:" + e.getMessage());
                break metka;
            }

            if (arr != null) {
                for (VSData data : arr) {
                    String query = "INSERT INTO result(source, target, weight, original_term) VALUES(' \n" +
                            "" + data.getSourse() + "', '" + data.getTarget() + "', '" + data.getWeight() + "', '" + VSData.getOriginal_therm() + "');";

                    statement.executeUpdate(query);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException:" + e.getMessage());
        }
    }

    public static void closeConnection (){
        try{
            if(statement!= null) {
                statement.close();
                System.out.println("Statement is close.................................................");
            }
            if(resultSet!= null) {
                resultSet.close();
                System.out.println("ResultSet close.................................................");
            }
            if(connection!= null) {
                connection.close();
                System.out.println("Connection close.................................................");
            }
        }
        catch (SQLException e){
            System.out.println("SQLException:" + e.getMessage());
        }
    }

    public static ArrayList<String> getTarget(int id) {
        ArrayList<String> targetList = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.getResultSet();

            String query = "SELECT target FROM result WHERE id <= "+ (id + 1);

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

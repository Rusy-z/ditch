package DataConvert;

import DataConvert.VSData;
import com.sun.tools.javac.Main;

import java.sql.*;
import java.util.ArrayList;

public class BDConnection {
    private static final String password = "Baal112Hammon";
    private static final String user = "root";
    private static final String url = "jdbc:mysql://localhost:3306/test";

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    private static final  int requestCount = XMLParser.vsDataArray.size();

    public static void getConnection (ArrayList<VSData> data) {
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            System.out.println("......................Connection exist..........................");
            String insert = "";
            statement.executeUpdate("use test");

            for (int i = 0; i < data.size(); i++) {
                VSData v = data.get(i);
                String query = "insert into result(source, target, weight, original_term) values(' \n" +
                        ""+ v.getSourse() + "', '" + v.getTarget() + "', '" + v.getWeight()+"', '" + VSData.getOriginal_therm() + "');";

                statement.executeUpdate(query);
                //System.out.println(query);
            }
            statement.close();
            connection.close();
            System.out.println("......................Connection close...........................");
        }
        catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
        }

    }
    public static ArrayList<String> getTarget() {
        ArrayList<String> targetList = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.getResultSet();

            String query = "select target from result";

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

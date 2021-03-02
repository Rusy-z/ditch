import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DatabaseConnection {
    private String password;
    private String user;
    private String url;
    private String databaseName;
    private String tableName;

    private int targetID = 0;

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    DatabaseConnection(){
        Properties properties = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            properties.load(fis);
            this.url = properties.getProperty("db.host");
            this.user = properties.getProperty("db.user");
            this.password = properties.getProperty("db.password");
            this.databaseName = properties.getProperty("db.name");
            this.tableName = properties.getProperty("db.table");
        }
        catch (IOException e){ System.out.println("IOException: " + e.getMessage()); }
    }

    public void getConnection () {
        System.out.println("Метод getConnection: Создаем соединение с бд");

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            statement.executeUpdate("USE " + databaseName);

        }
        catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
        }
    }

    public void InsertRequestForTestTable(String source, String target, int weight, String originalSource){
        System.out.println("Метод InsertRequestForTestTable: Отправляем INSERT запрос в таблицу result в бд с параметрами: \n" +
                "'" + source + "', '" + target + "', '" + weight + "', '" + originalSource +"'");

        try {
            String query = String.format("INSERT INTO %s (source, target, weight, original_term) "+
                            " VALUES ('%s','%s','%s','%s');", tableName, source, target, weight, originalSource);

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

    public ArrayList<String> getTarget() {
        System.out.println("Метод getTarget: Получаем массив вторичных заросов из бд");

        ArrayList<String> targetList = new ArrayList<>();
        try {
            resultSet = statement.getResultSet();

            String query = String.format("SELECT target FROM %s WHERE id >= %d",tableName, targetID);

            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                String target = resultSet.getString("target");
                targetList.add(target);
            }
        }
        catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        }
        targetID = targetID + targetList.size();

        return targetList;
    }
}

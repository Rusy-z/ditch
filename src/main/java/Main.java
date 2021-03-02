import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        final String word = args[0];
        int queryCount;
        try {
            queryCount = Integer.parseInt(args[1]);
        }catch (Exception e){
            queryCount =2;
        }

        GoogleQueries googleQueries = new GoogleQueries(word);
        ArrayList<String> arr = googleQueries.getStringTargetArrayList();
        System.out.println(arr);

        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.getConnection();

        System.out.println("Последнее id в бд: " + (databaseConnection.getTarget().size()+1));
        databaseConnection.closeConnection();


        System.out.println("Начльный запрос: " + word + "\n:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        databaseConnection.getConnection();
        for (int i = 0; i < arr.size(); i++) {
            if (i == 5) break;
            String target = arr.get(i);
            databaseConnection.InsertRequestForTestTable(word, target, (5 - i), word);
        }
        databaseConnection.closeConnection();

        while (queryCount > 1){
            databaseConnection.getConnection();
            ArrayList<String> targetList = databaseConnection.getTarget();

            for (String target : targetList) {
                System.out.println("Запрос по слову: " + target);
                GoogleQueries gQ = new GoogleQueries(target);
                String source = gQ.getQuery();
                ArrayList<String> list = gQ.getStringTargetArrayList();
                if (!list.isEmpty()) {
                    System.out.println("Массив ответов по слову : " + target + "\n" + list);
                    for (int j = 0; j < list.size(); j++) {
                        if (j == 5) break;
                        String nextTarget = list.get(j);
                        databaseConnection.InsertRequestForTestTable(source, nextTarget, (5 - j), word);
                    }
                }
                System.out.println("------------------------------------------------------------");
            }
            databaseConnection.closeConnection();
            System.out.println(queryCount);
            queryCount--;
        }
    }
}

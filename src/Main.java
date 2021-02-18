import DataConvert.BDConnection;
import DataConvert.GoogleQueries;
import DataConvert.VSData;
import DataConvert.XMLParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static int targetID = 0;

    public static void main(String[] args) {
        String word = args[0];
        String count = args[1];
        int queryCount = 1;
        try {
            queryCount = Integer.parseInt(count);
        }
        catch (ClassFormatError e){ System.out.println(e.getMessage()); }
        VSData.setOriginal_therm(word);

        GoogleQueries googleQueries = new GoogleQueries();
        BDConnection bdConnection = new BDConnection();

        System.out.println("Начльный запрос: " + word);
        System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        setQuery(word, googleQueries, bdConnection);

        int i = 1;

        while (queryCount > 1){
            System.out.println("Обрабатываем "+ i +" слой вторичных запросов");
            System.out.println();
            System.out.println("Main-метод: получаем массив строк со вторичными запросами:");
            ArrayList<String> array = bdConnection.getTarget(targetID);
            System.out.println(array);
            targetID = targetID + array.size();
            bdConnection.closeConnection();
            for (String query : array) {
                System.out.println("Вторичный запрос: " + query);
                setQuery(query, googleQueries, bdConnection);
                System.out.println();
            }
            queryCount--;
            i++;
        }
    }

    public static void setQuery (String query, GoogleQueries googleQueries, BDConnection bdConnection) {
        String url = googleQueries.getURL(query);
        File fileFromGoogle = googleQueries.getXmlFile(url);
        ArrayList<VSData> dataArrayList = null;
        try {
            dataArrayList = XMLParser.xmlParser(fileFromGoogle);
        }
        catch (SAXException e) {
            System.out.println(e.getMessage());
        }
        catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Метод setQuery: отправляем данные из массива в БД...");

        if (dataArrayList != null) {
            for (VSData data : dataArrayList) {
                bdConnection.getConnection();
                bdConnection.InsertRequestForTestTable(data);
                bdConnection.closeConnection();
            }
        }

        System.out.println("////////////////////////////////////////////////////");
    }
}

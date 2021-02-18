import DataConvert.BDConnection;
import DataConvert.URLTest;
import DataConvert.VSData;
import DataConvert.XMLParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static String firstRequest;
    private static ArrayList<VSData> dataForDatabase;
    private static ArrayList<String> targetInDatabase;
    private static int requestCount = 0;
    private static int summaryLayerCount;

    public static void main(String[] args) {
        System.out.println("Чтение первого запроса и колличества слоев");
        firstRequest = "Russia";
        summaryLayerCount = 4;

        VSData.setOriginal_therm(firstRequest);

        System.out.println("Отправляем первый запрос в поисковик из Main...");
        setRequest(firstRequest);
        System.out.println("///////////////////////////////////////////////////////////////");

        int startLayerCount = 1;
        while (summaryLayerCount > 1) {

            System.out.println("Получаем " + (startLayerCount) + " слой запроса" );
            targetInDatabase = getTargetArray();

            if (targetInDatabase != null) {
                setNextRequest();
            }
            summaryLayerCount--;
            startLayerCount++;
        }
    }
        public static void setRequest (String request){
            try{
                File xmlFIle = URLTest.URLtoXML(request);

                System.out.println("Получаем xml-файл...");
                dataForDatabase = XMLParser.xmlParser(xmlFIle);
                requestCount = dataForDatabase.size();

                System.out.println("Создаем соединение с бд и вносим в нее данные из массива данных...");
                BDConnection.getConnection();
                BDConnection.InsertRequestForTastTable(dataForDatabase);
            }
            catch (SAXException e) {
                System.out.println("SAXException: " + e.getMessage());
            }
            catch (ParserConfigurationException e){
                System.out.println("ParserConfigurationException: " + e.getMessage());
            }
            catch (IOException e){
                System.out.println("IOException: " + e.getMessage());
            }
            finally {
                System.out.println("Закрываем соединение с бд...");
                BDConnection.closeConnection();
                System.out.println("////////////////////////////////////////////////////////////////////////////////");
                System.out.println();
            }
        }

        public static ArrayList<String> getTargetArray(){
            ArrayList<String> list;
            try {
                BDConnection.getConnection();
                list = BDConnection.getTarget(requestCount);
            }
            finally {
                BDConnection.closeConnection();
            }
            return list;
        }

        public static void setNextRequest () {
            ArrayList<String> array = targetInDatabase;
            if (array != null) {
                for (String word : array) {
                    System.out.println("Поиск по слову: " + word);
                    setRequest(word);
                }
                requestCount = requestCount + array.size();
            }
        }
}

import DataConvert.BDConnection;
import DataConvert.URLTest;
import DataConvert.VSData;
import DataConvert.XMLParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    private int requestCount ;
    public static void main(String[] args) throws IOException {
        //чтение первого запроса
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String firstRequest = reader.readLine();
        VSData.setOriginal_therm(firstRequest);
        URLTest.URLtoString(firstRequest);

        //setRequest(firstRequest);


        //получение вторичных запросов и их чтение
        ArrayList<String> targetInDatabase;
        targetInDatabase = BDConnection.getTarget();

        for (int i = 0 ; i < targetInDatabase.size(); i ++){
            System.out.println("Request for: " + targetInDatabase.get(i));
            setRequest(targetInDatabase.get(i));
        }

    }
        public static void setRequest (String request){

            request = request.replace("+", "%2B");
            request = request.replace("#", "%23");

            String url = "http://suggestqueries.google.com/complete/search?&output=toolbar&gl=us&hl=en&q=%3C"+ request +"%20vs%20%3E";

            //создаем хмл файл(???) и отправляем его парсить

            try {
                File file = URLTest.URLtoXML(url);
                XMLParser.xmlParser(file);
            }
            catch (SAXException e) {
                System.out.println("SAXException: "+ e.getMessage());
            }
            catch (ParserConfigurationException e){
                System.out.println("ParserConfigurationException: " + e.getMessage());
            }
            catch (IOException e){
                System.out.println("IOException: " + e.getMessage());
            }
        }

}

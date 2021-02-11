package DataConvert;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class XMLParser  {
    // "senceArray" - массив строк из хмл-файла
    // "vsDataArray" - массив объектов типа VSData,который содержит: первичный запрос, вториный запрос,
    // массу запроса, оригинальынй запрос
    public static ArrayList<String> senceArray = new ArrayList<>();
    public static ArrayList<VSData> vsDataArray = new ArrayList<>();
    public static void xmlParser (File file) throws ParserConfigurationException, SAXException , IOException {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        //парсим наш хмл-файл
        //методом parse заполняется массив senceArray строками результата запроса
        XMLHandler xmlHandler = new XMLHandler();
        parser.parse(file, xmlHandler);
        //System.out.println(senceArray);

        // заполняем массив данными, полученными из строк результата запроса
        vsDataArray = StringParser(senceArray);
        senceArray.clear();

        //System.out.println(vsDataArray);
        BDConnection.getConnection(vsDataArray);
        vsDataArray.clear();

    }

    // метод разбивает строку по разделителю "vs"
    // получаем из строки первичный запрос, вторичный запрос, массу запроса и оригинальный запрос
    public static ArrayList<VSData> StringParser (ArrayList<String> arr){
        ArrayList<VSData> list = new ArrayList<>();
        int startWeigth = 5;

        for (int i = 0; i < arr.size(); i++) {
            String string  = arr.get(i);
            String[] split = string.split(" vs ");

            String a = split[0], b = split[1];

            if (b.length() <= 10 && list.size() < 5) {
                list.add(new VSData(a, b, startWeigth));
                startWeigth--;
            }
            else {
                arr.remove(i);
                i--;
            }
        }
        return list;
    }

    // создаем вложеный класс, который парсит хмл по елементам suggestion и получает атрибут data в виде строки
    // добавляем строку в массив строк для ее обоработки
    public static class XMLHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String name, Attributes attributes) {
            if (name.equals("suggestion")) {
                String s = attributes.getValue("data");
                senceArray.add(s);
            }
        }

    }
}




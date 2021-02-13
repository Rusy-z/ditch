package DataConvert;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XMLParser  {
    // "senceArray" - массив строк из хмл-файла
    // "vsDataArray" - массив объектов типа VSData,который содержит: первичный запрос, вториный запрос,
    // массу запроса, оригинальынй запрос
    public static ArrayList<String> senceArray = new ArrayList<>();
    public static ArrayList<VSData> vsDataArray = new ArrayList<>();
    public static ArrayList<VSData> xmlParser (File file) throws ParserConfigurationException, SAXException , IOException {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        //парсим наш хмл-файл
        //методом parse заполняется массив senceArray строками результата запроса
        XMLHandler xmlHandler = new XMLHandler();
        System.out.println("Парсим xml-файл...");
        parser.parse(file, xmlHandler);

        // заполняем массив данными, полученными из строк результата запроса
        System.out.println("Добавляем данные из xml-файла в массив...");
        vsDataArray = StringParser(senceArray);
        senceArray.clear();

        return vsDataArray;
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

    // метод разбивает строку по разделителю "vs"
    // получаем из строки первичный запрос, вторичный запрос, массу запроса и оригинальный запрос
    public static ArrayList<VSData> StringParser (ArrayList<String> arr){
        ArrayList<VSData> list = new ArrayList<>();
        int startWeigth = 5;

        for (int i = 0; i < arr.size(); i++) {
            String string  = arr.get(i);
            try {
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
            catch (ArrayIndexOutOfBoundsException e) {
                arr.remove(i);
                i--;
            }
        }
        return list;
    }
}




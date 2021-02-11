package DataConvert;

import org.xml.sax.SAXException;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.XMLFormatter;

public class URLTest {
    // наверное самый бесполезный кусок кода
    // создаем кривой хмл-файл (нахуя???)
        public static File URLtoXML (String s) throws IOException {
                 String answer = "";
                 URL url = new URL(s);
                 URLConnection con = url.openConnection();

                 InputStream inputStream = con.getInputStream();
                 File file = new File("1.xml");

                 OutputStream outputStream = new FileOutputStream(file);
                 while (inputStream.available() > 0){
                         int b = inputStream.read();
                         outputStream.write(b);
                 }

                 return file;
         }
         public static String URLtoString (String s) throws IOException {
            String answer = "";
             System.out.println(s);
            return answer;
         }
}

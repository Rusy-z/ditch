package DataConvert;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLTest {
    private static HttpURLConnection con;

    public static File URLtoXML (String s) throws IOException {
        s = s.replace("\\+", "%2B");
        s = s.replace("#", "%23");

        String urlAdress = "http://suggestqueries.google.com/complete/search?&output=toolbar&gl=us&hl=en&q=%3C"+ s +"%20vs%20%3E";

        try {
            System.out.println("Создаем HttpConnection...");
            URL url = new URL(urlAdress);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            File file = new File("1.xml");
            FileOutputStream outputStream;
            System.out.println("Получаем xml-файл...");

            try(InputStream inputStream = con.getInputStream()) {
                outputStream = new FileOutputStream(file);
                while (inputStream.available() > 0){
                    int b = inputStream.read();
                    outputStream.write(b);
                }
            }
        return file;
        }
        finally {
            System.out.println("Закрываем HttpConnection...");
            con.disconnect();
        }
    }


    public static void URLtoStringArray (String s) throws IOException {
        s = s.replace("+", "%2B");
        s = s.replace("#", "%23");

        String urlAdress = "http://suggestqueries.google.com/complete/search?&output=toolbar&gl=us&hl=en&q=%3C"+ s +"%20vs%20%3E";
        String answer;
        try {
            System.out.println("Открываем соединение");
            URL url = new URL(urlAdress);
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            System.out.println("Типо читаем что-то");
            StringBuilder conten = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    conten.append(line);
                    conten.append(System.lineSeparator());
                }
            }
            answer = conten.toString();

            System.out.println(answer);
        }
        finally {
            System.out.println("Закрываем соединение");
            con.disconnect();
        }
    }
}


import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GoogleQueries {
    private HttpURLConnection con;
    private String query;
    private ArrayList<String> stringTargetArrayList = new ArrayList<>();

    public GoogleQueries(String googleQuery){
        this.query = googleQuery;
        start();
    }

    private void start() {
        String url = getUrlQuery(query);
        ArrayList<String> responseArray = getResponse(url);

            while (stringTargetArrayList.size() < 5) {
                if (responseArray.isEmpty()) break;

                    for (int i = 0; i < responseArray.size(); i++) {
                    String string = responseArray.get(i);
                    String[] splitString = string.split(" vs ");
                    stringTargetArrayList.add(splitString[1]);
                }
            }
        }


    public String getQuery(){
        return this.query;
    }

    public ArrayList<String> getStringTargetArrayList() {
        return stringTargetArrayList;
    }

    private String getUrlQuery (String query){
        query = query.replace("\\+", "%2B");
        query = query.replace("#", "%23");
        query = query.replace(" ", "+");
        String stringURL = "http://suggestqueries.google.com/complete/search?&output=toolbar&gl=us&hl=en&q=" + query + "%20vs%20%";

        return stringURL;
    }

    private ArrayList<String> getResponse (String stringURL){
        ArrayList<String> response = new ArrayList();
        try {
            //Создаем HttpConnection...
            URL url = new URL(stringURL);
            System.out.println(url);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String string = reader.readLine();
                String[] splitArray = string.split("\"");
                for (String s : splitArray) {
                    if (s.contains(" vs ")){
                        response.add(s);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            //Закрываем HttpConnection...
            con.disconnect();
        }
        return response;
    }
}



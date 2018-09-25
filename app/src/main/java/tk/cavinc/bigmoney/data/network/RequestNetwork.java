package tk.cavinc.bigmoney.data.network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import tk.cavinc.bigmoney.data.models.CurseModel;
import tk.cavinc.bigmoney.data.models.RequestSheetModel;

/**
 * Created by cav on 25.09.18.
 */

public class RequestNetwork {

    private String getRequestMessage( HttpURLConnection conn) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
        StringBuilder x = new StringBuilder();
        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
            x.append(output);
        }
        return x.toString();
    }

    // заполняем заголовок запроса
    private void setHeadParams(HttpURLConnection conn){
        /* setRequestProperty */
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
    }

    public RequestNetwork(){

    }

    public ArrayList<String> getBank(){
        ArrayList<String> rec = new ArrayList<>();
        try {
            URL url = new URL("http://ptkrus.ru/bigmoney.php?a=n");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");

            setHeadParams(conn);
            conn.connect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                String res = getRequestMessage(conn);
                JSONArray jsonObject = new JSONArray(res);
                for (int i=0;i<jsonObject.length();i++){
                    rec.add(jsonObject.getString(i));
                }
            }
            conn.disconnect();
        } catch (Exception e){
            e.printStackTrace();
        }
        return rec;
    }

    public ArrayList<String> getValute(){
        ArrayList<String> rec = new ArrayList<>();
        try {
            URL url = new URL("http://ptkrus.ru/bigmoney.php?a=c");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");

            setHeadParams(conn);
            conn.connect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                String res = getRequestMessage(conn);
                JSONArray jsonObject = new JSONArray(res);
                for (int i=0;i<jsonObject.length();i++){
                    rec.add(jsonObject.getString(i));
                }
            }
            conn.disconnect();

        } catch (Exception e){
            e.printStackTrace();
        }
        return rec;
    }

    public ArrayList<CurseModel> getCurse(){
        ArrayList<CurseModel> rec = new ArrayList<>();
        try {
            URL url = new URL("http://ptkrus.ru/bigmoney.php?a=x");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");

            setHeadParams(conn);
            conn.connect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                String res = getRequestMessage(conn);
                JSONArray jsonObject = new JSONArray(res);
                for (int i=0;i<jsonObject.length();i++){
                    JSONArray lx = jsonObject.getJSONArray(i);
                    rec.add(new CurseModel(lx.getString(0),lx.getString(1),lx.getDouble(2)));
                }
            }
            conn.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rec;
    }

    public ArrayList<RequestSheetModel> getSheet(){
        ArrayList<RequestSheetModel> rec = new ArrayList<>();

        try {
            URL url = new URL("http://ptkrus.ru/bigmoney.php?a=b");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");

            setHeadParams(conn);
            conn.connect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                String res = getRequestMessage(conn);
                JSONArray jsonObject = new JSONArray(res);
                for (int i=0;i<jsonObject.length();i++){
                    JSONArray lx = jsonObject.getJSONArray(i);
                    rec.add(new RequestSheetModel(lx.getString(0),lx.getString(2),lx.getString(1),lx.getDouble(3)));
                }
            }
            conn.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rec;
    }
}

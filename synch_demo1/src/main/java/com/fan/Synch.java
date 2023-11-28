package com.fan;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Synch {

    public static final String loginReq ="http://192.168.110.216:8080/api/v1/login";
    public static final String loginLoad = "{\n" +
            "  \"user\": \"bytesynch\",\n" +
            "  \"password\": \"bytesynch123\",\n" +
            "  \"username\": \"bytesynch\"\n" +
            "}";

    public static String authorizationCode;

    public static final String crmReq = "http://192.168.110.216:8080/api/v1/hubs/xschub/channels/crm/refresh";
    public static final String crmReqLoad = "{\n" +
            "  \"source_loc\": \"crmsource\",\n" +
            "  \"target_loc\": \"crmtarget\",\n" +
            "  \"granularity\": \"bulk\",\n" +
            "  \"task\": \"refr\",\n" +
            "  \"start_immediate\": true\n" +
            "}";
    public static final String oacrmcrmReq = "http://192.168.110.216:8080/api/v1/hubs/xschub/channels/oacrmcrm/refresh";
    public static final String oacrmcrmReqLoad = "{\n" +
            "  \"source_loc\": \"oacrmsource2\",\n" +
            "  \"target_loc\": \"oacrmtarget2\",\n" +
            "  \"granularity\": \"bulk\",\n" +
            "  \"task\": \"refr\",\n" +
            "  \"start_immediate\": true\n" +
            "}";
    public static final String oacrmReq = "http://192.168.110.216:8080/api/v1/hubs/xschub/channels/oacrm/refresh";
    public static final String oacrmReqqLoad = "{\n" +
            "  \"source_loc\": \"oacrmsource\",\n" +
            "  \"target_loc\": \"oacrmtarget\",\n" +
            "  \"granularity\": \"bulk\",\n" +
            "  \"task\": \"refr\",\n" +
            "  \"start_immediate\": true\n" +
            "}";


    public static void main(String[] args) {
        while (true){
            doSynch(crmReq, crmReqLoad, false);
            doSynch(oacrmcrmReq, oacrmcrmReqLoad, false);
            doSynch(oacrmReq, oacrmReqqLoad, false);
        }
    }

    public static void doSynch(String req, String load, Boolean needNewAuthorizationCode){

        if(needNewAuthorizationCode){
            req = loginReq;
            load = loginLoad;
        }else {
            try {
                // 线程延时 5分钟
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            URL url = new URL(req);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置请求方法为 POST
            connection.setRequestMethod("POST");

            // 设置请求头
            connection.setRequestProperty("Content-Type", "application/json");
            if (!needNewAuthorizationCode){
                connection.setRequestProperty("Authorization", "Bearer "+authorizationCode);
            }

            // 允许输出
            connection.setDoOutput(true);

            // 获取输出流
            try (OutputStream os = connection.getOutputStream()) {
                // 将 JSON 数据写入输出流
                byte[] input = load.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            if (responseCode == 401){
                System.out.println("登录有问题");
                doSynch(req, load, true);
                return;
            }

            // 读取响应内容
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                // 打印响应内容
                System.out.println("Response Content: " + response.toString());
                if (needNewAuthorizationCode){
                    authorizationCode = response.toString();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


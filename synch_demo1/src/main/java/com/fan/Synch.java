package com.fan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Synch {

    public static String authorizationCode;


    public static final String loginReq ="http://192.168.110.216:8080/api/v1/login";
    public static final String loginLoad = "{\n" +
            "  \"user\": \"bytesynch\",\n" +
            "  \"password\": \"bytesynch123\",\n" +
            "  \"username\": \"bytesynch\"\n" +
            "}";

    public static int count;

    public static void main(String[] args) {
        SynchList[] values = SynchList.values();
        while (true){
            for (SynchList s : values){
                doSynch(s.getReq(), s.getLoad(), s.getName(),false);
            }
        }
    }


    public static void doSynch(String req, String load,String name, Boolean needNewAuthorizationCode){
        count++;
        if(needNewAuthorizationCode){
            req = loginReq;
            load = loginLoad;
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
                count--;
                doSynch(req, load,name ,true);
                doSynch(req, load,name, false);
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
                    count--;
                }else {
                    System.out.println("已同步次数："+count);
                    System.out.println("当前同步的是："+name);
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!needNewAuthorizationCode){
            try {
                if (req.equals(SynchList.CRM.getReq())){
                    // crm延时 4分钟
                    Thread.sleep(240000);
                }else{
                    Thread.sleep(120000);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

enum SynchList {

    CRM("http://192.168.110.216:8080/api/v1/hubs/xschub/channels/crm/refresh","{\n" +
            "  \"source_loc\": \"crmsource\",\n" +
            "  \"target_loc\": \"crmtarget\",\n" +
            "  \"granularity\": \"bulk\",\n" +
            "  \"task\": \"refr\",\n" +
            "  \"start_immediate\": true\n" +
            "}","CRM"),

    OACRMCRM("http://192.168.110.216:8080/api/v1/hubs/xschub/channels/oacrmcrm/refresh","{\n" +
            "  \"source_loc\": \"oacrmsource2\",\n" +
            "  \"target_loc\": \"oacrmtarget2\",\n" +
            "  \"granularity\": \"bulk\",\n" +
            "  \"task\": \"refr\",\n" +
            "  \"start_immediate\": true\n" +
            "}","OACRMCRM"),
    OACRM("http://192.168.110.216:8080/api/v1/hubs/xschub/channels/oacrm/refresh","{\n" +
            "  \"source_loc\": \"oacrmsource\",\n" +
            "  \"target_loc\": \"oacrmtarget\",\n" +
            "  \"granularity\": \"bulk\",\n" +
            "  \"task\": \"refr\",\n" +
            "  \"start_immediate\": true\n" +
            "}","OACRM"),
    jzpttocrmdev("http://192.168.110.216:8080/api/v1/hubs/xschub/channels/jzpttocrmd/refresh",
            "{\n" +
                    "  \"source_loc\": \"jzptcrm1dev\",\n" +
                    "  \"target_loc\": \"jzptcrm2dev\",\n" +
                    "  \"granularity\": \"bulk\",\n" +
                    "  \"task\": \"refr\",\n" +
                    "  \"start_immediate\": true\n" +
                    "}","jzpttocrmdev"),

    jzpttocrmprod("http://192.168.110.216:8080/api/v1/hubs/xschub/channels/jzpttocrmp/refresh",
            "{\n" +
                    "  \"source_loc\": \"jzptcrm1prod\",\n" +
                    "  \"target_loc\": \"jzptcrm2prod\",\n" +
                    "  \"granularity\": \"bulk\",\n" +
                    "  \"task\": \"refr\",\n" +
                    "  \"start_immediate\": true\n" +
                    "}","jzpttocrmprod"),
    jzptdevnew("http://192.168.110.216:8080/api/v1/hubs/xschub/channels/jzptdev11122/refresh",
            "{\n" +
                    "  \"source_loc\": \"jzptdev111\",\n" +
                    "  \"target_loc\": \"jzptdev222\",\n" +
                    "  \"granularity\": \"bulk\",\n" +
                    "  \"task\": \"refr\",\n" +
                    "  \"start_immediate\": true\n" +
                    "}","jzptdevnew"),
    JZPTPROD("http://192.168.110.216:8080/api/v1/hubs/xschub/channels/jzptproda/refresh","{\n" +
            "  \"source_loc\": \"jzptproda1\",\n" +
            "  \"target_loc\": \"jzptproda2\",\n" +
            "  \"granularity\": \"bulk\",\n" +
            "  \"task\": \"refr\",\n" +
            "  \"start_immediate\": true\n" +
            "}","JZPTPROD");
//    JZPTDEV("http://192.168.110.216:8080/api/v1/hubs/xschub/channels/jzptdev/refresh","{\n" +
//            "  \"source_loc\": \"source\",\n" +
//            "  \"target_loc\": \"target\",\n" +
//            "  \"granularity\": \"bulk\",\n" +
//            "  \"task\": \"refr\",\n" +
//            "  \"start_immediate\": true\n" +
//            "}","JZPTDEV");

    private String req;
    private String load;
    private String name;

    SynchList(String req, String load,String name) {
        this.req = req;
        this.load = load;
        this.name = name;
    }

    public String getReq() {
        return req;
    }

    public String getLoad() {
        return load;
    }

    public String getName() {
        return name;
    }
}


package com.example.typhoonvision001.Function;

import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetWorks {

    private static final String FLAG = "NetWorks";

    private static  String NetWorksIS(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();
        os.close();
        return state;
    }

    public static String MethodGet(String url){
        HttpURLConnection con =null;
        try{
            URL url1 =new URL(url);
            con = (HttpURLConnection) url1.openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(5000);
            con.setConnectTimeout(10000);

            int responseCode = con.getResponseCode();
            if(responseCode == 200){
                InputStream is = con.getInputStream();
                String response = NetWorksIS(is);
                Log.i(FLAG,"访问成功"+ responseCode + ":" + response);
                return response;
            }else {
                Log.i(FLAG,"访问失败" + responseCode);
                return "1";
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(con != null){
                con.disconnect();
            }
        }
        return  "1";
    }
}
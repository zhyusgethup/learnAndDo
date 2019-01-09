package com.cellsgame;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

class ParameterStringBuilder {
    public static String getParamsString(Map<String, Integer> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, Integer> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(entry.getValue());
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}

public class TestAddGoodsByHTTP {
    public static void main(String args[]) throws IOException {
        URL url = null;

        url = new URL("http://192.168.10.117:7001/game/changeGoods");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
//        con.setRequestProperty("Content-Type", "application/json");
        con.setReadTimeout(5000);
        con.setConnectTimeout(5000);

        Map<String, Integer> parameters = new HashMap<>();
        parameters.put("playerId", 10);
        parameters.put("goodsCid", 10);
        parameters.put("changeNum", 1);

        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
        out.flush();
    }
}

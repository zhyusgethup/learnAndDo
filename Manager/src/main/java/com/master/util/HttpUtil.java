package com.master.util;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.gmdesign.exception.GmException;
import com.master.analyze.http.HttpPropertiesLoader;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.*;

public class HttpUtil {
    private static  Logger log = LoggerFactory.getLogger(HttpUtil.class);
    public static String httpPost(String url, Map<String, String> map) {
        log.debug("url {} , map {}", url ,map);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            HttpPost post = new HttpPost(builder.build());
            if(null != map && map.size() > 0) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                post.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
            }
            response = httpClient.execute(post);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                byte[] bytes = new byte[(int) entity.getContentLength()];
                InputStream content = entity.getContent();
                content.read(bytes);
                return new String(bytes);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Object gameServerReturnObjectPost(String url, Map<String, String> map) throws GmException{
        String str = HttpUtil.httpPost(url, map);
        if(null != str) {
            HashMap hashMap = JSON.parseObject(str, HashMap.class);
            if(hashMap.containsKey("ret")){
                return hashMap.get("ret");
            }else{
                new GmException("运行错误");
            }
        }
        return null;
    }

    public static void gameServerReturnVoidPost(String url,Map<String, String> map) throws GmException{
        String str = HttpUtil.httpPost(url, map);
        if(null != str) {
            HashMap hashMap = JSON.parseObject(str, HashMap.class);
            System.out.println(hashMap);
            if(hashMap.containsKey("code")){
                if(Integer.valueOf((String)hashMap.get("code")) == 0)
                    return;
            }
        }
        throw new GmException("运行错误");
    }
    public static void loginServerReturnVoidPost(String url,Map<String, String> map) throws GmException{
        String str = HttpUtil.httpPost(url, map);
        if(null != str) {
            HashMap hashMap = JSON.parseObject(str, HashMap.class);
            System.out.println(hashMap);
            if(hashMap.containsKey("co")){
                if((int)hashMap.get("co") == 0)
                    return;
            }
        }
        throw new GmException("运行错误");
    }
    public static Object loginServerReturnObjectPost(String url,Map<String, String> map) throws GmException{
        String str = HttpUtil.httpPost(url, map);
        if(null != str) {
            HashMap hashMap = JSON.parseObject(str, HashMap.class);
            System.out.println(hashMap);
            if(hashMap.containsKey("data")){
                return hashMap.get("data");
            }else{
                if(hashMap.containsKey("msg")){
                    throw new GmException((String)hashMap.get("msg"));
                }
            }
        }
        throw new GmException("运行错误");
    }
}

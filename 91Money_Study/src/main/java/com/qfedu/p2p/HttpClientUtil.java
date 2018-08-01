package com.qfedu.p2p;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;

/**
 *@Author feri
 *@Date Created in 2018/7/31 17:01
 */
public class HttpClientUtil {
    public static String getPage(String url){
        HttpClient client=HttpClientBuilder.create().build();
        HttpGet get=new HttpGet();
        get.setURI(URI.create(url));
        try {
            HttpResponse response= client.execute(get);
            return EntityUtils.toString(response.getEntity(),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

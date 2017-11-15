package com.mix;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class SecurityHelper {
    private static String privateKey = "jP9xl59o";
    
    /**
     * @param str
     * @return
     * @throws Exception
     */
    public static String encrypt(String str) throws Exception {
        return encrypt(str, privateKey);
    }


    public static String encrypt(String str,String encrypt_key) throws Exception {
        DES des;
        while(str.getBytes().length%8!=0){
            str+=" ";
        }
        String tmp =  str;
        des = new DES(encrypt_key);
        return des.encrypt(tmp);
    }
    
    /**
     * @param str
     * @return
     * @throws Exception
     */
    public static String decrypt(String str) throws Exception {
        DES des;
        des = new DES(privateKey);
        String tmp = des.decrypt(str);
        return tmp.trim();
    }
    
    public static String decrypt(String str,String encrypt_key) throws Exception {
        DES des;
        des = new DES(encrypt_key);
        String tmp = des.decrypt(str);
        return tmp.trim();
    }

    @Test
    public void test() throws Exception {
        String a = encrypt("18817350807");
        System.out.println(a);
    }

    public static void main(String args[]) {
        String url = "http://basketballgame.pernod-ricard-china.com/chivas2/increase";
        //url = "http://localhost:3001/chivas2/increase";
        //获取可关闭的 httpCilent
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();

        HttpPost httpPost = new HttpPost(url);
        //设置超时时间
        httpPost.setConfig(requestConfig);

        try {
            //装配post请求参数
            List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
            //String pack = encrypt("{\"openId\": \"123456789\", \"stickerId\": \"123456789\"}");
            String pack = Base64.encode("{\"openId\": \"o3vWouDWPxCcDL8415Ism-rUoFK4\", \"stickerId\": \"test\"}".getBytes());
            list.add(new BasicNameValuePair("pack", pack));  //请求参数
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8");
            //设置post求情参数
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String strResult = "";
            if(httpResponse != null){
                System.out.println(httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                } else if (httpResponse.getStatusLine().getStatusCode() == 400) {
                    strResult = "Error Response: " + httpResponse.getStatusLine().toString();
                } else if (httpResponse.getStatusLine().getStatusCode() == 500) {
                    strResult = "Error Response: " + httpResponse.getStatusLine().toString();
                } else {
                    strResult = "Error Response: " + httpResponse.getStatusLine().toString();
                }
            }else{

            }
            System.out.println(strResult);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(httpClient != null){
                    httpClient.close(); //释放资源
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}


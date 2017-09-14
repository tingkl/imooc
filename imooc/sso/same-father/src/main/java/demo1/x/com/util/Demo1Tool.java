package demo1.x.com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tingkl on 2017/9/11.
 */
public class Demo1Tool {
    public static String doGet(String urlStr, String cookieName, String cookieValue) {
        StringBuffer sb = new StringBuffer();
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(urlStr + "?cookieName=" + cookieName + "&cookieValue=" + cookieValue);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStream in = httpURLConnection.getInputStream();

            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);
            String temp = null;
            while ((temp = br.readLine())!=null) {
                sb.append(temp);
            }
            br.close();
            isr.close();
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (httpURLConnection !=null) {
                httpURLConnection.disconnect();
            }
        }
        return sb.toString();
    }
}

package www.a.com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by tingkl on 2017/9/11.
 */
public class Demo1Tool {
    public static String doGet(String urlStr, Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        HttpURLConnection httpURLConnection = null;
        try {
            StringBuffer t_s = new StringBuffer(urlStr).append("?");
            for (Map.Entry<String, String> entry: map.entrySet()) {
                t_s.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            urlStr = t_s.substring(0, t_s.length() - 1);

            URL url = new URL(urlStr);
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

package com.dajie;

import com.google.gson.Gson;
import com.Msg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Main {
    private static String readFile(String filePath) {
        File file = new File(filePath);
        FileInputStream is;
        StringBuilder stringBuilder = null;
        try {
            if (file.length() != 0) {
                /**
                 * 文件有内容才去读文件
                 */
                is = new FileInputStream(file);
                InputStreamReader streamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(streamReader);
                String line;
                stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    // stringBuilder.append(line);
                    stringBuilder.append(line);
                }
                reader.close();
                is.close();
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(stringBuilder);

    }
    public static void main(String[] args) {
        Msg msg = new Msg();
        Gson gSon = new Gson();
        if (args.length < 3) {
            msg.setSuccess(false);
            msg.setMsg("method key text, need three params");
        } else {
            String method = args[0];
            String key = args[1];
            String text = readFile(args[2]);
            String result = "";
            boolean hasError = false;
            try {
                if (method.equals("decrypt")) {
                    result = DESUtil.decrypt(text, key);
                } else {
                    result = DESUtil.encrypt(text, key);
                }
            } catch (Exception e) {
                msg.setSuccess(false);
                hasError = true;
                msg.setMsg(e.getMessage());
            }
            if (!hasError) {
                msg.setSuccess(true);
                msg.setMsg(result);
            }
        }
        System.out.println(gSon.toJson(msg));
    }
}

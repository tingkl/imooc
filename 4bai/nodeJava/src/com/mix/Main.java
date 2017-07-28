package com.mix;

import com.google.gson.Gson;

public class Main {

    public static void main(String[] args) {
        Msg msg = new Msg();
        Gson gSon = new Gson();

        if (args.length < 3) {
            msg.setSuccess(false);
            msg.setMsg("method key text, need three params");
        } else {
            String method = args[0];
            String key = args[1];
            String text = args[2];
            String result = "";
            boolean hasError = false;
            try {
                if (method.equals("decrypt")) {
                    result = SecurityHelper.decrypt(text, key);
                } else {
                    result = SecurityHelper.encrypt(text, key);
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

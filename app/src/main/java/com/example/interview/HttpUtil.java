package com.example.interview;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {

    public static void sendRequestWithOkHttp(String add, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(add).build();
        client.newCall(request).enqueue(callback);
    }
}

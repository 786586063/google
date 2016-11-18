package com.wanghe.google.http;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.framed.FrameReader;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogRecord;

/**
 * Created by 两三人 on 2016/11/18.
 * 封装类
 */

public class OkhttpManger {

    public static final String url = "http://127.0.0.1:8090/";
    private OkHttpClient client;
    private static OkhttpManger okhttpManger;
    private Handler mHandler;
    private OkhttpManger(){
        client = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());

    }
    /*
       单例模式
     */
    private static OkhttpManger getInstance(){
        if (okhttpManger == null){
            okhttpManger = new OkhttpManger();
        }
        return okhttpManger;
    }

    //对外公布的方法 <<<<<<<子线程中>>>>>>>>>>
    public static Response getSync(String url)throws IOException{
        return getInstance().p_getSync(url);
    }

    //内部逻辑处理
    private Response p_getSync(String url)throws IOException{
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return  response;
    }
    //对外公布，返回String 类型的数据
    public  static String getSyncString(String url)throws IOException{

        return getInstance().p_getSyncString(url);

    }
    private String p_getSyncString(String url)throws IOException{
        return p_getSync(url).body().string();
    }

    //异步回调
    public static void getAsync(String url,DataCallBack callBack) {
        getInstance().p_getAsync(url,callBack);
    }
    private void p_getAsync(String url, final DataCallBack callBack){
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                deliveDataFailure(request,e,callBack);
            }

            @Override
            public void onResponse(Response response)  {

                try {
                    String relust = response.body().string();
                    deliveDataSuccess(relust,callBack);

                } catch (IOException e) {
                    deliveDataFailure(request,e,callBack);
                }

            }
        });
    }
    //数据分发>>>>>>>>>失败
    private void deliveDataFailure(final Request request, final IOException e, final DataCallBack callBack){

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null){
                    callBack.requestFailure(request,e);
                }
            }
        });

    }
    //数据分发>>>>>>>>>成功
    private void deliveDataSuccess(final String result, final DataCallBack callBack){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null){
                    callBack.requestSuccess(result);
                }
            }
        });
    }
    //post提交表单回调
    public static void postAsync(String url, Map<String,String> params,DataCallBack callBack){

        getInstance().p_postAsync(url,params,callBack);
    }
    private void p_postAsync(String url, Map<String,String> params, final DataCallBack callBack){
        RequestBody requestBody = null;
         if (params == null){
            params = new HashMap<String,String>();
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Map.Entry<String,String> entry:params.entrySet()) {
            String key = entry.getKey().toString();
            String value = null;
            if (entry.getValue() == null){
                value = "";
            }else{
                value = entry.getValue().toString();
            }
            builder.add(key,value);
        }
        requestBody = builder.build();
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                deliveDataFailure(request,e,callBack);
            }

            @Override
            public void onResponse(Response response) throws IOException {

                try {
                    String relust = response.body().string();
                    deliveDataSuccess(relust,callBack);

                } catch (IOException e) {
                    deliveDataFailure(request,e,callBack);
                }
            }
        });
    }
    //数据回调接口
    public interface DataCallBack{

        void requestFailure(Request request,IOException e);
        void requestSuccess(String result);
    }
}

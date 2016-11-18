package com.wanghe.google.http.protocol;

import com.squareup.okhttp.Response;
import com.wanghe.google.http.OkhttpManger;
import com.wanghe.google.utils.IOUtils;
import com.wanghe.google.utils.LogUtils;
import com.wanghe.google.utils.StringUtils;
import com.wanghe.google.utils.UIUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 访问网络的基类
 * Created by 两三人 on 2016/11/18.
 */

public abstract class BaseProcotol<T> {

    public T getData(int index){
        //先判断是否有缓存
        String result = getCache(index);

        if (StringUtils.isEmpty(result)){
            //如果没有缓存，或者缓存失效
            result = getDataFromServer(index);

        }
        //开始解析
        if (result != null){
            T parseData = parseData(result);
            return parseData;
        }

        return null;
    }

    //index表示从哪个位置开始请求数据，用于分页
    public String getDataFromServer(int index) {
        try {
            String result= OkhttpManger.getSyncString(OkhttpManger.url+getKey()
                    +"?index="+ index + getParams());
            if (!StringUtils.isEmpty(result)){
                LogUtils.e(result);
                //写缓存
                if (!StringUtils.isEmpty(result)){
                    setCache(index,result);
                }
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //获取网络连接的关键词，子类必须实现
    public abstract String getKey();
    //获取网络连接参数
    public abstract String getParams();

    //写缓存 以url为文件名，以json为文件内容，保存在本地
    public  void setCache(int index,String json){
        File cacheDir = UIUtils.getContext().getCacheDir();
        //生成缓存文件
        File cacheFile = new File(cacheDir,getKey()
                +"?index="+ index + getParams());
        FileWriter writer = null;
        try {
            writer = new FileWriter(cacheFile);
            long deadline = System.currentTimeMillis()+30*60*1000;//半小时的有效期
            writer.write(deadline+"\n"); // 在第一行写入缓存时间，换行
            writer.write(json);
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(writer);
        }
    }
    public String getCache(int index){

        File cacheDir = UIUtils.getContext().getCacheDir();
        //生成缓存文件
        File cacheFile = new File(cacheDir,getKey()
                +"?index="+ index + getParams());
        if (cacheFile.exists()){
            //判断缓存有效期
            BufferedReader reader = null;
            try {

                reader = new BufferedReader(new FileReader(cacheFile));
                String readline = reader.readLine();
                long deadtime = Long.parseLong(readline);
                if (System.currentTimeMillis() < deadtime){
                    //缓存有效
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line = reader.readLine())!= null){
                        sb.append(line);
                    }
                    return sb.toString();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                IOUtils.close(reader);
            }
        }

        return null;
    }
    public abstract T parseData(String result);
}

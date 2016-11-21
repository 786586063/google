package com.wanghe.google.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by 两三人 on 2016/11/19.
 */

public class RecommenfProcotol extends BaseProcotol<ArrayList<String>> {
    @Override
    public String getKey() {
        return "recommend";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<String> parseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);
            ArrayList<String>list = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                String words = ja.getString(i);
                list.add(words);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.wanghe.google.http.protocol;

import com.wanghe.google.domain.CategortInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 两三人 on 2016/11/21.
 */

public class CategoryProtocol extends BaseProcotol<ArrayList<CategortInfo>> {

    @Override
    public String getKey() {
        return "category";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<CategortInfo> parseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);
            ArrayList<CategortInfo> list  = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                CategortInfo titleInfo = new CategortInfo();
                JSONObject jo = (JSONObject) ja.get(i);
                //添加标题对象
                if (jo.has("title")){
                    String title = jo.getString("title");
                    titleInfo.isTitle = true;
                    titleInfo.title = title;
                    list.add(titleInfo);
                }
                if (jo.has("infos")){
                    JSONArray array = jo.getJSONArray("infos");
                    for (int j = 0; j < array.length(); j++) {
                        CategortInfo info = new CategortInfo();
                        JSONObject obj = (JSONObject) array.get(j);
                        info.name1 = obj.getString("name1");
                        info.name2 = obj.getString("name2");
                        info.name3 = obj.getString("name3");
                        info.url1 = obj.getString("url1");
                        info.url2 = obj.getString("url2");
                        info.url3 = obj.getString("url3");
                        info.isTitle = false;
                        list.add(info);
                    }
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}

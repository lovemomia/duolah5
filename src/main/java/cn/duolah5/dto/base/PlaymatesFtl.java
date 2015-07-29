package cn.duolah5.dto.base;

import cn.momia.common.web.img.ImageFile;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * Created by ysm on 15-7-24.
 */
public class PlaymatesFtl extends ArrayList<JSONObject> implements Dto {
    public PlaymatesFtl(JSONArray skusPlaymatesJson) {
        for (int i = 0; i < skusPlaymatesJson.size(); i++) {
            JSONObject skuPlaymatesJson = skusPlaymatesJson.getJSONObject(i);
            JSONArray playmatesJson = skuPlaymatesJson.getJSONArray("playmates");
            for (int j = 0; j < playmatesJson.size(); j++) {
                JSONObject playmateJson = playmatesJson.getJSONObject(j);
                playmateJson.put("avatar", ImageFile.url(playmateJson.getString("avatar")));
            }
            this.add(skuPlaymatesJson);
        }
    }
}

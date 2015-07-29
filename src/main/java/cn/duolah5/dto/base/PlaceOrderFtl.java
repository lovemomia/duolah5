package cn.duolah5.dto.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by ysm on 15-7-27.
 */
public class PlaceOrderFtl implements Dto {
    private JSONObject contacts;
    private JSONArray skus;

    public JSONObject getContacts() {
        return contacts;
    }

    public JSONArray getSkus() {
        return skus;
    }

    public PlaceOrderFtl(JSONObject contactsJson, JSONArray skusJson) {
        this.contacts = contactsJson;
        this.skus = skusJson;
    }
}
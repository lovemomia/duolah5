package cn.momia.duolah5.dto.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by ysm on 15-7-20.
 */
public class ProductOrderFtl implements Dto{
    private ContactsFtl contacts;
    private ListDto skus;

    public ContactsFtl getContacts() {
        return contacts;
    }

    public ListDto getSkus() {
        return skus;
    }

    public ProductOrderFtl(JSONObject userPackJson, JSONArray skusJson) {
        this.contacts = getContacts(userPackJson);
        this.skus = getSkus(skusJson);
    }

    private ContactsFtl getContacts(JSONObject userPackJson) {
        if (userPackJson == null) return null;

        JSONObject userJson = userPackJson.getJSONObject("user");
        if (userJson == null) return null;

        return new ContactsFtl(userJson);
    }

    private ListDto getSkus(JSONArray skusJson) {
        ListDto skus = new ListDto();
        for (int i = 0; i < skusJson.size(); i++) {
            skus.add(new SkuFtl(skusJson.getJSONObject(i)));
        }

        return skus;
    }

}

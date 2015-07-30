package cn.momia.duolah5.ftl.base;

import cn.momia.duolah5.ftl.composite.ListFtl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by ysm on 15-7-20.
 */
public class ProductOrderFtl implements Ftl {
    private ContactsFtl contacts;
    private ListFtl skus;

    public ContactsFtl getContacts() {
        return contacts;
    }

    public ListFtl getSkus() {
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

    private ListFtl getSkus(JSONArray skusJson) {
        ListFtl skus = new ListFtl();
        for (int i = 0; i < skusJson.size(); i++) {
            skus.add(new SkuFtl(skusJson.getJSONObject(i)));
        }

        return skus;
    }

}

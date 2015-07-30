package cn.momia.duolah5.ftl.base;



import cn.momia.duolah5.web.img.ImageFile;
import cn.momia.duolah5.ftl.misc.ProductUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by ysm on 15-7-20.
 */
public class ProductDetailFtl extends ProductFtl implements Ftl {
    private JSONObject customers;
    private String url;

    public JSONObject getCustomers() {
        return customers;
    }

    public String getUrl() {
        return url;
    }

    public ProductDetailFtl(JSONObject productJson, JSONObject customersJson) {
        super(ProductUtil.extractProductData(productJson, true));
        this.customers = processAvatars(customersJson);
        this.url = ProductUtil.buildUrl(getId());
    }

    private JSONObject processAvatars(JSONObject customersJson) {
        JSONArray avatarsJson = customersJson.getJSONArray("avatars");
        if (avatarsJson != null) {
            for (int i = 0; i < avatarsJson.size(); i++) {
                String avatar = avatarsJson.getString(i);
                avatarsJson.set(i, ImageFile.url(avatar));
            }
        }

        return customersJson;
    }
}

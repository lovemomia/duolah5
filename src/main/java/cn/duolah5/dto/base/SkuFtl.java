package cn.duolah5.dto.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;

/**
 * Created by ysm on 15-7-20.
 */
public class SkuFtl implements Dto{
    private long productId;
    private long skuId;
    private String desc;
    private int type;
    private int limit;
    private boolean needRealName;
    private int stock;
    private BigDecimal minPrice;
    private String time;
    private JSONArray prices;

    public long getProductId() {
        return productId;
    }

    public long getSkuId() {
        return skuId;
    }

    public String getDesc() {
        return desc;
    }

    public int getType() {
        return type;
    }

    public int getLimit() {
        return limit;
    }

    public boolean isNeedRealName() {
        return needRealName;
    }

    public int getStock() {
        return stock;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public String getTime() {
        return time;
    }

    public JSONArray getPrices() {
        return prices;
    }

    public SkuFtl(JSONObject skuJson) {
        this.productId = skuJson.getLong("productId");
        this.skuId = skuJson.getLong("id");
        this.desc = skuJson.getString("desc");
        this.type = skuJson.getInteger("type");
        this.limit = skuJson.getInteger("limit");
        this.needRealName = skuJson.getBoolean("needRealName");
        this.stock = skuJson.getInteger("unlockedStock");
        this.minPrice = skuJson.getBigDecimal("minPrice");
        this.time = skuJson.getString("s");
        this.prices = skuJson.getJSONArray("prices");

        if (limit < 0) limit = type == 1 ? 1 : 0;
        if (stock < 0) stock = 0;
    }
}

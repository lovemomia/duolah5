package cn.momia.duolah5.dto.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;

/**
 * Created by ysm on 15-7-17.
 */
public class OrderOfUserFtl implements Dto{
    private long id;
    private String cover;
    private String time;
    private BigDecimal totalFee;
    private String title;
    private String participants;
    private long productId;
    private long totalCount;
    private long skuId;
    private int status;

    public long getId() {
        return id;
    }

    public String getCover() {
        return cover;
    }

    public String getTime() {
        return time;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public String getTitle() {
        return title;
    }

    public String getParticipants() {
        return participants;
    }

    public long getProductId() {
        return productId;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public long getSkuId() {
        return skuId;
    }

    public int getStatus() {
        return status;
    }

    public OrderOfUserFtl(JSONObject orderJson) {
        this.cover = orderJson.getString("cover");
        this.time = orderJson.getString("time");
        this.title = orderJson.getString("title");

        JSONObject parseJson = orderJson.getJSONObject("order");
        this.id = parseJson.getLong("id");
        this.productId = parseJson.getLong("productId");
        this.skuId = parseJson.getLong("skuId");
        this.participants = buildParticipants(parseJson.getJSONArray("prices"));
        this.totalFee = parseJson.getBigDecimal("totalFee");
        this.status = parseJson.getInteger("status");


    }

    private String buildParticipants(JSONArray prices) {
        int adult = 0;
        int child = 0;
        for (int i = 0; i < prices.size(); i++) {
            JSONObject price = prices.getJSONObject(i);
            int count = price.getInteger("count");
            adult += price.getInteger("adult") * count;
            child += price.getInteger("child") * count;
        }

        if (adult > 0 && child > 0) return adult + "成人, " + child + "儿童";
        else if (adult <= 0 && child > 0) return child + "儿童";
        else if (adult > 0 && child <= 0) return adult + "成人";
        return "";
    }
}

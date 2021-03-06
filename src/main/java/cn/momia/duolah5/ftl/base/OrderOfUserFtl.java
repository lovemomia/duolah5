package cn.momia.duolah5.ftl.base;

import cn.momia.duolah5.web.img.ImageFile;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ysm on 15-7-17.
 */
public class OrderOfUserFtl implements Ftl {
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
    private int count;
    private String contacts;
    private String mobile;
    @JSONField(format = "yyyy-mm-dd") private Date addTime;
    private String scheduler;
    private BigDecimal price;
    private String address;

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

    public int getCount() {
        return count;
    }

    public String getContacts() {
        return contacts;
    }

    public String getMobile() {
        return mobile;
    }

    public Date getAddTime() {
        return addTime;
    }

    public String getScheduler() {
        return scheduler;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getAddress() {
        return address;
    }

    public OrderOfUserFtl(JSONObject orderPackJson) {
        this(orderPackJson, false);
    }


    public OrderOfUserFtl(JSONObject orderPackJson, boolean extractExtraInfo) {

        this.id = orderPackJson.getInteger("id");
        this.productId = orderPackJson.getLong("productId");
        this.skuId = orderPackJson.getLong("skuId");
        this.count = orderPackJson.getInteger("count");
        this.totalFee = orderPackJson.getBigDecimal("totalFee");
        this.participants =orderPackJson.getString("participants");
        this.contacts = orderPackJson.getString("contacts");
        this.mobile = orderPackJson.getString("mobile");
        this.addTime = orderPackJson.getDate("addTime");
        this.status = orderPackJson.getInteger("status");

        if (extractExtraInfo) {
            this.cover = ImageFile.largeUrl(orderPackJson.getString("cover"));
            this.title = orderPackJson.getString("title");
            this.scheduler = orderPackJson.getString("scheduler");
            this.price = orderPackJson.getBigDecimal("price");
            this.address = orderPackJson.getString("address");
         //   this.time = orderPackJson.getString("time");
        }
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

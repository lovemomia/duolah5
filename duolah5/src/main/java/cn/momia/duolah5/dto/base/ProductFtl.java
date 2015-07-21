package cn.momia.duolah5.dto.base;

import cn.momia.common.config.Configuration;
import cn.momia.common.web.img.ImageFile;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ysm on 15-7-17.
 */
public class ProductFtl implements Dto{
    private long id;
    private String cover;
    private String thumb;
    private String title;
    private String abstracts;
    private int joined;
    private int sales;
    private boolean soldOut;
    private BigDecimal price;
    private String crowd;
    private String scheduler;
    private String address;
    private String poi;
    private JSONArray tags;
    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date startTime;
    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date endTime;

    // extra info
    private List<String> imgs;
    private JSONArray content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public int getJoined() {
        return joined;
    }

    public void setJoined(int joined) {
        this.joined = joined;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(boolean soldOut) {
        this.soldOut = soldOut;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCrowd() {
        return crowd;
    }

    public void setCrowd(String crowd) {
        this.crowd = crowd;
    }

    public String getScheduler() {
        return scheduler;
    }

    public void setScheduler(String scheduler) {
        this.scheduler = scheduler;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPoi() {
        return poi;
    }

    public void setPoi(String poi) {
        this.poi = poi;
    }

    public JSONArray getTags() {
        return tags;
    }

    public void setTags(JSONArray tags) {
        this.tags = tags;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public JSONArray getContent() {
        return content;
    }

    public void setContent(JSONArray content) {
        this.content = content;
    }

    public  ProductFtl(){}

    public ProductFtl(ProductFtl product) {
        this.id = product.id;
        this.cover = product.cover;
        this.thumb = product.thumb;
        this.title = product.title;
        this.abstracts = product.abstracts;
        this.joined = product.joined;
        this.sales = product.sales;
        this.soldOut = product.soldOut;
        this.price = product.price;
        this.crowd = product.crowd;
        this.scheduler = product.scheduler;
        this.address = product.address;
        this.poi = product.poi;
        this.tags = product.tags;
        this.startTime = product.startTime;
        this.endTime = product.endTime;

        this.imgs = product.imgs;
        this.content = product.content;
    }
}



package cn.momia.duolah5.ftl.base;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by ysm on 15-7-20.
 */
public class BannerFtl implements Ftl {
    private String cover;
    private String action;

    public String getCover() {
        return cover;
    }

    public String getAction() {
        return action;
    }

    public BannerFtl(JSONObject bannerJson) {
        if(org.apache.commons.lang3.StringUtils.isBlank(bannerJson.toString())) {
            this.cover = bannerJson.getString("cover");
            this.action = bannerJson.getString("action");
        }
    }
}

package cn.momia.duolah5.ftl.composite;

import cn.momia.duolah5.ftl.base.Ftl;
import com.alibaba.fastjson.JSONArray;

public class HomeFtl implements Ftl {
    public static final HomeFtl EMPTY = new HomeFtl();
    static {
        EMPTY.setProducts(new JSONArray());
    }

    private JSONArray banners;
    private JSONArray products;
    private Integer nextpage = null;

    public JSONArray getBanners() {
        return banners;
    }

    public void setBanners(JSONArray banners) {
        this.banners = banners;
    }

    public JSONArray getProducts() {
        return products;
    }

    public void setProducts(JSONArray products) {
        this.products = products;
    }

    public Integer getNextpage() {
        return nextpage;
    }

    public void setNextpage(Integer nextpage) {
        this.nextpage = nextpage;
    }
}

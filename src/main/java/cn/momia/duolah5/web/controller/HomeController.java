package cn.momia.duolah5.web.controller;


import cn.momia.duolah5.web.http.MomiaHttpParamBuilder;
import cn.momia.duolah5.web.http.MomiaHttpRequest;
import cn.momia.duolah5.web.http.MomiaHttpResponseCollector;
import cn.momia.duolah5.web.response.ResponseMessage;
import cn.momia.duolah5.ftl.base.Ftl;
import cn.momia.duolah5.ftl.composite.HomeFtl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/index.html")
public class HomeController extends BaseFunc {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView home() {
        return new ModelAndView("home", "home", "");

    }

    private List<MomiaHttpRequest> buildHomeRequests(int cityId, int start, int count) {
        List<MomiaHttpRequest> requests = new ArrayList<MomiaHttpRequest>();
        if (start == 0) requests.add(buildBannersRequest(cityId));
        requests.add(buildProductsRequest(cityId, start, count));

        return requests;
    }

    private MomiaHttpRequest buildBannersRequest(int cityId) {
        int count = conf.getInt("Home.BannerCount");
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("city", cityId)
                .add("count", count);

        return MomiaHttpRequest.GET("banners", true, url("banner"), builder.build());
    }

    private MomiaHttpRequest buildProductsRequest(int cityId, int start, int count) {
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("city", cityId)
                .add("start", start)
                .add("count", count);

        return MomiaHttpRequest.GET("products", true, url("product"), builder.build());
    }

    private Ftl buildHomeDto(MomiaHttpResponseCollector collector, int start, int count, int pageIndex) {
        HomeFtl homeDto = new HomeFtl();

        if (pageIndex == 0) homeDto.setBanners((JSONArray) collector.getResponse("banners"));

        JSONObject productsPackJson = (JSONObject) pagedProductsFunc.apply(collector.getResponse("products"));
        homeDto.setProducts(productsPackJson.getJSONArray("list"));

        long totalCount = productsPackJson.getLong("totalCount");
        if (start + count < totalCount) homeDto.setNextpage(pageIndex + 1);

        return homeDto;
    }
}

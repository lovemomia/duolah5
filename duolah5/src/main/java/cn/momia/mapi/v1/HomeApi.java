package cn.momia.mapi.v1;

import cn.momia.common.web.http.MomiaHttpParamBuilder;
import cn.momia.common.web.http.MomiaHttpRequest;
import cn.momia.common.web.http.MomiaHttpResponseCollector;
import cn.momia.duolah5.dto.base.BannerFtl;
import cn.momia.mapi.api.AbstractApi;
import cn.momia.mapi.dto.composite.HomeDto;
import cn.momia.mapi.dto.composite.ListDto;
import cn.momia.mapi.dto.misc.ProductUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/index.html")
public class HomeApi extends AbstractApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeApi.class);

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView home(@RequestParam(value = "pageindex") final int pageIndex, @RequestParam(value = "city") int cityId) {
        final int maxPageCount = conf.getInt("Home.MaxPageCount");
        final int pageSize = conf.getInt("Home.PageSize");
        if (pageIndex >= maxPageCount || cityId < 0) return new ModelAndView("BadRequest", "msg","invalid params");

        List<MomiaHttpRequest> requests = buildHomeRequests(pageIndex, cityId);
        MomiaHttpResponseCollector collector = executeRequests(requests);
        HomeDto homeDto = new HomeDto();

        if (pageIndex == 0) homeDto.setBanners(extractBannerData((JSONArray) collector.getResponse("banners")));


        JSONObject productsPackJson = (JSONObject) collector.getResponse("products");

        JSONArray productsJson = productsPackJson.getJSONArray("products");
        homeDto.setProducts(ProductUtil.extractProductsData(productsJson));

        long totalCount = productsPackJson.getLong("totalCount");
        if (pageIndex < maxPageCount - 1 && (pageIndex + 1) * pageSize < totalCount) homeDto.setNextpage(pageIndex + 1);
        List list = new ArrayList();
        Map<String, Object> homeMap = new HashMap<String, Object>();
        homeMap.put("banner", homeDto.getBanners());
        homeMap.put("products", homeDto.getProducts());
        homeMap.put("nextPage", homeDto.getNextpage());
        list.add(homeMap);
        return new ModelAndView("home", "home", list);
    }

    private List<MomiaHttpRequest> buildHomeRequests(int pageIndex, int cityId) {
        List<MomiaHttpRequest> requests = new ArrayList<MomiaHttpRequest>();
        if (pageIndex == 0) requests.add(buildBannersRequest(cityId));
        requests.add(buildProductsRequest(pageIndex, cityId));

        return requests;
    }

    private MomiaHttpRequest buildBannersRequest(int cityId) {
        int count = conf.getInt("Home.BannerCount");
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("city", cityId)
                .add("count", count);

        return MomiaHttpRequest.GET("banners", true, baseServiceUrl("banner"), builder.build());
    }

    private MomiaHttpRequest buildProductsRequest(int pageIndex, int cityId) {
        int pageSize = conf.getInt("Home.PageSize");
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("city", cityId)
                .add("start", String.valueOf(pageIndex * pageSize))
                .add("count", String.valueOf(pageSize));

        return MomiaHttpRequest.GET("products", true, baseServiceUrl("product"), builder.build());
    }

    private ListDto extractBannerData(JSONArray bannersJson) {
        ListDto banners = new ListDto();
        for (int i = 0; i < bannersJson.size(); i++) {
            try {
                banners.add(new BannerFtl(bannersJson.getJSONObject(i)));
            } catch (Exception e) {
                LOGGER.error("fail to parser the {}th banner", i, e);
            }
        }

        return banners;
    }
}

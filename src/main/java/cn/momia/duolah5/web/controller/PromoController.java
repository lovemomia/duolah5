package cn.momia.duolah5.web.controller;

import cn.momia.duolah5.web.http.MomiaHttpParamBuilder;
import cn.momia.duolah5.web.http.MomiaHttpRequest;
import cn.momia.duolah5.web.response.ResponseMessage;
import cn.momia.duolah5.wx.WxConfig;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PromoController extends BaseFunc {
    private static final Logger LOGGER = LoggerFactory.getLogger(PromoController.class);

    @RequestMapping(value = "/m/promo/share", method = RequestMethod.GET)
    public ModelAndView shareM(HttpServletRequest request) {
        return share("promo/share_m", request);
    }

    public ModelAndView share(String ftl, HttpServletRequest httpRequest) {
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("city", 1)  // TODO 其它城市支持
                .add("start", 0)
                .add("count", 20);
        MomiaHttpRequest request = MomiaHttpRequest.GET(url("product"), builder.build());
        ResponseMessage response = executeRequest(request, pagedProductsFunc);

        if (!response.successful()) return new ModelAndView("BadRequest", "errmsg", "error");

        JSONObject result = new JSONObject();
        try {
            result.put("config", new WxConfig(conf.getString("Weixin.JsApiAppId"), httpRequest.getRequestURL() + "?" + httpRequest.getQueryString()));
            StringBuilder urlBuilder = new StringBuilder().append(httpRequest.getRequestURL());

            String utoken = getUtoken(httpRequest);
            if (!StringUtils.isBlank(utoken)) {
                String code = getUserInviteCode(utoken);
                if (!StringUtils.isBlank(code)) urlBuilder.append("&invite=").append(code);
            }
            result.put("url", urlBuilder.toString());
        } catch (Exception e) {
            LOGGER.warn("fail to generate share config");
        }

        List<JSONObject> products = new ArrayList<JSONObject>();
        JSONObject pagedProducts = (JSONObject) response.getData();
        JSONArray list = pagedProducts.getJSONArray("list");
        BigDecimal threshold = new BigDecimal(1);
        for (int i = 0; i < list.size(); i++) {
            JSONObject product = list.getJSONObject(i);
            if (product.getBigDecimal("price").compareTo(threshold) > 0) products.add(product);
        }

        result.put("products", products);

        return new ModelAndView(ftl, "result", result);
    }

    @RequestMapping(value = "/promo/share", method = RequestMethod.GET)
    public ModelAndView shareApp(HttpServletRequest request) {
        return share("promo/share_app", request);
    }
}

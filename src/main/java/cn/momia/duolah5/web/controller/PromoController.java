package cn.momia.duolah5.web.controller;

import cn.momia.duolah5.web.http.MomiaHttpParamBuilder;
import cn.momia.duolah5.web.http.MomiaHttpRequest;
import cn.momia.duolah5.web.response.ResponseMessage;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/promo")
public class PromoController extends BaseFunc {
    @RequestMapping(value = "/share", method = RequestMethod.GET)
    public ModelAndView share() {
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("city", 1)  // TODO 其它城市支持
                .add("start", 0)
                .add("count", 20);
        MomiaHttpRequest request = MomiaHttpRequest.GET(url("product"), builder.build());
        ResponseMessage response = executeRequest(request, pagedProductsFunc);

        if (!response.successful()) return new ModelAndView("BadRequest", "errmsg", "error");

        List<JSONObject> products = new ArrayList<JSONObject>();
        JSONObject pagedProducts = (JSONObject) response.getData();
        JSONArray list = pagedProducts.getJSONArray("list");
        BigDecimal threshold = new BigDecimal(1);
        for (int i = 0; i < list.size(); i++) {
            JSONObject product = list.getJSONObject(i);
            if (product.getBigDecimal("price").compareTo(threshold) > 0) products.add(product);
        }
        return new ModelAndView("promo/share", "products", products);
    }
}

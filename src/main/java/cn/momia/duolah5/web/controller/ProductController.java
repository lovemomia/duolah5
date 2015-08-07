package cn.momia.duolah5.web.controller;

import cn.momia.duolah5.ftl.base.Ftl;
import cn.momia.duolah5.ftl.base.ParticipantFtl;
import cn.momia.duolah5.ftl.composite.ListFtl;
import cn.momia.duolah5.web.http.MomiaHttpParamBuilder;
import cn.momia.duolah5.web.http.MomiaHttpRequest;
import cn.momia.duolah5.web.http.MomiaHttpResponseCollector;
import cn.momia.duolah5.web.img.ImageFile;
import cn.momia.duolah5.web.response.ResponseMessage;
import cn.momia.duolah5.ftl.base.PlaceOrderFtl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class ProductController extends BaseFunc {
    private static final Logger LOGGER =  LoggerFactory.getLogger(ProductController.class);

    @RequestMapping(value = "/actsDetail.html", method = RequestMethod.GET)
    public ModelAndView getProduct(@RequestParam(defaultValue = "") String utoken, @RequestParam long id, HttpServletRequest httpRequest) {
        if (id <= 0) return new ModelAndView("BadRequest", "errmsg","invalid params");

        utoken = getUtoken(httpRequest);
        List<MomiaHttpRequest> requests = buildProductRequests(utoken, id);

        ResponseMessage responseMessage = executeRequests(requests, new Function<MomiaHttpResponseCollector, Object>() {
            @Override
            public Object apply(MomiaHttpResponseCollector collector) {
                JSONObject productJson = (JSONObject) productFunc.apply(collector.getResponse("product"));
                JSONObject customersJson = (JSONObject) collector.getResponse("customers");

                productJson.put("customers", processAvatars(customersJson));

                boolean opened = productJson.getBoolean("opened");
                if (!opened) productJson.put("soldOut", true);

                return productJson;
            }
        });

        if(responseMessage.getErrno() != 0)
            return new ModelAndView("BadRequest", "errmsg", "error");

        List list = new ArrayList();
        list.add(responseMessage.getData());
        return new ModelAndView("./product/product", "product", list);
    }

    private List<MomiaHttpRequest> buildProductRequests(String utoken, long productId) {
        List<MomiaHttpRequest> requests = new ArrayList<MomiaHttpRequest>();
        requests.add(buildProductRequest(utoken, productId));
        requests.add(buildProductCustomersRequest(productId));

        return requests;
    }

    private MomiaHttpRequest buildProductRequest(String utoken, long productId) {
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);

        return MomiaHttpRequest.GET("product", true, url("product", productId), builder.build());
    }

    private MomiaHttpRequest buildProductCustomersRequest(long productId) {
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("start", 0)
                .add("count", conf.getInt("Product.CustomerPageSize"));

        return MomiaHttpRequest.GET("customers", false, url("product", productId, "customer"), builder.build());
    }

    private JSONObject processAvatars(JSONObject customersJson) {
        JSONArray avatarsJson = customersJson.getJSONArray("avatars");
        if (avatarsJson != null) {
            for (int i = 0; i < avatarsJson.size(); i++) {
                String avatar = avatarsJson.getString(i);
                avatarsJson.set(i, ImageFile.url(avatar));
            }
        }

        return customersJson;
    }

    @RequestMapping(value = "/orderDetail.html", method = RequestMethod.GET)
    public ModelAndView getProductOrder(HttpServletRequest httpRequest, @RequestParam long id) {
        String utoken = getUtoken(httpRequest);
        if(StringUtils.isBlank(utoken) || id <= 0) return new ModelAndView("BadRequest", "errmsg","invalid params");

        List<MomiaHttpRequest> requests = buildProductOrderRequests(id, utoken);

        ResponseMessage responseMessage =  executeRequests(requests, new Function<MomiaHttpResponseCollector, Object>() {
            @Override
            public Object apply(MomiaHttpResponseCollector collector) {
                return new PlaceOrderFtl((JSONObject) collector.getResponse("contacts"), (JSONArray) collector.getResponse("skus"));
            }
        });
        if(responseMessage.getErrno() != 0)
            return new ModelAndView("BadRequest", "errmsg", "error!");
        List list = new ArrayList();
        list.add(responseMessage.getData());
        return new ModelAndView("./product/sku", "skus", list);
    }

    private List<MomiaHttpRequest> buildProductOrderRequests(long productId, String utoken) {
        List<MomiaHttpRequest> requests = new ArrayList<MomiaHttpRequest>();
        requests.add(buildContactsRequest(utoken));
        requests.add(buildProductSkusRequest(productId));

        return requests;
    }

    private MomiaHttpRequest buildContactsRequest(String utoken) {
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET("contacts", true, url("user/contacts"), builder.build());

        return request;
    }

    private MomiaHttpRequest buildProductSkusRequest(long productId) {
        return MomiaHttpRequest.GET("skus", true, url("product", productId, "sku"));
    }

    @RequestMapping(value = "/partner.html", method = RequestMethod.GET)
    public ModelAndView getProductPlaymates(@RequestParam long id) {
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("start", 0)
                .add("count", conf.getInt("Product.Playmate.MaxSkuCount"));
        MomiaHttpRequest request = MomiaHttpRequest.GET(url("product", id, "playmate"), builder.build());

        ResponseMessage responseMessage = executeRequest(request, new Function<Object, Object>() {
            @Override
            public Object apply(Object data) {
                JSONArray skusPlaymatesJson = (JSONArray) data;
                for (int i = 0; i < skusPlaymatesJson.size(); i++) {
                    JSONObject skuPlaymatesJson = skusPlaymatesJson.getJSONObject(i);
                    JSONArray playmatesJson = skuPlaymatesJson.getJSONArray("playmates");
                    for (int j = 0; j < playmatesJson.size(); j++) {
                        JSONObject playmateJson = playmatesJson.getJSONObject(j);
                        playmateJson.put("avatar", ImageFile.url(playmateJson.getString("avatar")));
                    }
                }

                return data;
            }
        });
        if(responseMessage.getErrno() != 0)
            return new ModelAndView("BadRequest", "errmsg", "error!");
        List list = new ArrayList();
        list.add(responseMessage.getData());
        return new ModelAndView("./product/playmates", "playmates", list);
    }

    @RequestMapping(value = "/choose_Outer.html", method = RequestMethod.GET)
    public ModelAndView getParticipants(HttpServletRequest httpRequest) {
        String utoken = getUtoken(httpRequest);

        if (StringUtils.isBlank(utoken)) return new ModelAndView("success", "msg", "invalid param");
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(url("participant"), builder.build());
        ResponseMessage responseMessage =  executeRequest(request);
        if(responseMessage.getErrno() != 0 )
            return new ModelAndView("BadRequest", "errmsg", "error!");

        List list = new ArrayList();
        list.add(responseMessage.getData());
        return new ModelAndView("./product/sku_participant", "participant", list);
    }

    @RequestMapping(value = "/outer_info.html")
    public ModelAndView getContacts() {
        return new ModelAndView("./product/contacts");
    }

    @RequestMapping(value = "/topic.html", method = RequestMethod.GET)
    public ModelAndView getProductDetail (@RequestParam long id) {
        return null;

    }


}

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

    @RequestMapping(value = "/actsDetail", method = RequestMethod.GET)
    public ModelAndView getProduct(@RequestParam(defaultValue = "") String utoken, @RequestParam long id, HttpServletRequest httpRequest) {
        if (id <= 0) return new ModelAndView("BadRequest", "errmsg","invalid params");

        utoken = getUtoken(httpRequest);
        List<MomiaHttpRequest> requests = buildProductRequests(utoken, id);

        ResponseMessage responseMessage = executeRequests(requests, new Function<MomiaHttpResponseCollector, Object>() {
            @Override
            public Object apply(MomiaHttpResponseCollector collector) {
                JSONObject productJson = (JSONObject) productFunc.apply(collector.getResponse("product"));
                JSONArray customersJson = (JSONArray) collector.getResponse("customers");

                productJson.put("customers", buildCustomers(customersJson, productJson.getInteger("stock")));

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
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("type", 4);

        return MomiaHttpRequest.GET("product", true, url("product", productId), builder.build());
    }

    private MomiaHttpRequest buildProductCustomersRequest(long productId) {
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("pid", productId)
                .add("start", 0)
                .add("count", conf.getInt("Product.CustomerPageSize"));

        return MomiaHttpRequest.GET("customers", false, url("order/customer"), builder.build());
    }

    private JSONObject buildCustomers(JSONArray avatarsJson, int stock) {
        JSONObject customersJson = new JSONObject();
        customersJson.put("text", "玩伴信息" + ((stock > 0 && stock <= conf.getInt("Product.StockAlert")) ? "（仅剩" + stock + "个名额）" : ""));
        customersJson.put("avatars", processAvatars(avatarsJson));

        return customersJson;
    }

    private JSONArray processAvatars(JSONArray avatarsJson) {
        if (avatarsJson != null) {
            for (int i = 0; i < avatarsJson.size(); i++) {
                String avatar = avatarsJson.getString(i);
                avatarsJson.set(i, ImageFile.url(avatar));
            }
        }

        return avatarsJson;
    }

    @RequestMapping(value = "/orderDetail", method = RequestMethod.GET)
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
        return new ModelAndView("./product/get_sku", "skus", list);
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

    @RequestMapping(value = "/partner", method = RequestMethod.GET)
    public ModelAndView getProductPlaymates(@RequestParam long id) {
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("pid", id)
                .add("start", 0)
                .add("count", conf.getInt("Product.Playmate.MaxSkuCount"));
        MomiaHttpRequest request = MomiaHttpRequest.GET(url("order/playmate"), builder.build());

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

    @RequestMapping(value = "/choose_Outer", method = RequestMethod.GET)
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

    @RequestMapping(value = "/outer_info")
    public ModelAndView getContacts() {
        return new ModelAndView("./product/contacts");
    }

    @RequestMapping(value = "/topic.html", method = RequestMethod.GET)
    public ModelAndView getProductDetail (@RequestParam long id) {
        MomiaHttpRequest request = MomiaHttpRequest.GET(url("topic", id));
        ResponseMessage responseMessage = executeRequest(request);
        if(responseMessage.getErrno() != 0)
            return new ModelAndView("BadRequest", "errmsg", "error!");

        JSONObject topicJson = (JSONObject)responseMessage.getData();
        topicJson.put("cover", ImageFile.url(topicJson.getString("cover")));
        processCoverJson(topicJson.getJSONArray("groups"));

        return new ModelAndView("./product/topicProduct", "topic", topicJson);

    }

    private void processCoverJson(JSONArray productArray) {

        for(int i = 0; i < productArray.size(); i++) {
            JSONArray productsJson = productArray.getJSONObject(i).getJSONArray("products");
            for(int j = 0; j < productsJson.size(); j++) {
                JSONObject productJson = productsJson.getJSONObject(j);
                productJson.put("cover", ImageFile.url(productJson.getString("cover")));
            }

        }
    }


}

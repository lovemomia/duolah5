package cn.momia.mapi.v1;

import cn.momia.common.web.http.MomiaHttpParamBuilder;
import cn.momia.common.web.http.MomiaHttpRequest;
import cn.momia.common.web.http.MomiaHttpResponseCollector;
import cn.momia.duolah5.common.HttpExecute;
import cn.momia.duolah5.dto.base.ProductDetailFtl;
import cn.momia.duolah5.dto.base.ProductFtl;
import cn.momia.duolah5.dto.base.ProductOrderFtl;
import cn.momia.mapi.api.AbstractApi;
import cn.momia.mapi.dto.misc.ProductUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping
public class ProductApi extends AbstractApi {
    private static final Logger LOGGER =  LoggerFactory.getLogger(ProductApi.class);

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView getProducts(@RequestParam(value = "city") final int cityId,
                                       @RequestParam final int start,
                                       @RequestParam final int count,
                                       @RequestParam(required = false) String query) {
        final int maxPageCount = conf.getInt("Product.MaxPageCount");
        final int pageSize = conf.getInt("Product.PageSize");
        if (cityId < 0 || start < 0 || count <= 0 || start > maxPageCount * pageSize) return new ModelAndView("BadRequest", "msg","invalid params");
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("city", cityId)
                .add("start", start)
                .add("count", count)
                .add("query", query);
        MomiaHttpRequest request = MomiaHttpRequest.GET(baseServiceUrl("product"), builder.build());

        JSONObject responseJson = new HttpExecute().getJsonObject(request);
        List list = new ArrayList();
        Map<String, Object> products = buildProductsDto(responseJson.getJSONObject("data"), start, count);
        list.add(products);
        return new ModelAndView("./product/products", "products", list);
    }

    private Map<String, Object> buildProductsDto(JSONObject productsPackJson, int start, int count) {
        Map<String, Object> products = new HashMap<String, Object>();
        List<ProductFtl> productList = new ArrayList<ProductFtl>();

        long totalCount = productsPackJson.getLong("totalCount");
        products.put("totalCount", totalCount);
        if (start + count < totalCount) products.put("nextIndex", start + count);

        JSONArray productsJson = productsPackJson.getJSONArray("products");
        for (int i = 0; i < productsJson.size(); i++) {
            try {
                productList.add(new ProductUtil().extractProductData(productsJson.getJSONObject(i), false));
            } catch (Exception e) {
                LOGGER.error("fail to parse product: ", productsJson.getJSONObject(i), e);
            }
        }
        products.put("products", productList);

        return products;
    }

    @RequestMapping(value = "/actsDetail.html", method = RequestMethod.GET)
    public ModelAndView getProduct(@RequestParam final long id) {
        if (id <= 0) return new ModelAndView("BadRequest", "msg","invalid params");

        List<MomiaHttpRequest> requests = buildProductRequests(id);
        MomiaHttpResponseCollector collector = executeRequests(requests);
        if(!collector.isSuccessful())  return new ModelAndView("BadRequest", "msg","fail to get product");
            List list = new ArrayList();
            //Map<String, Object> product = buildProduct((JSONObject) collector.getResponse("product"), (JSONObject) collector.getResponse("customers"));
        list.add(new ProductDetailFtl((JSONObject) collector.getResponse("product"), (JSONObject) collector.getResponse("customers")));

        return new ModelAndView("./product/product", "product", list);


    }
    private Map<String, Object> buildProduct(JSONObject productPackJson, JSONObject customerPackJson) {
        Map<String, Object> product = new HashMap<String, Object>();
        ProductDetailFtl productDetail = new ProductDetailFtl(productPackJson, customerPackJson);
        List<ProductDetailFtl> productDetailFtlList = new ArrayList<ProductDetailFtl>();
        productDetailFtlList.add(productDetail);
        product.put("product", productDetailFtlList);
        return product;
    }

    private List<MomiaHttpRequest> buildProductRequests(long productId) {
        List<MomiaHttpRequest> requests = new ArrayList<MomiaHttpRequest>();
        requests.add(buildProductRequest(productId));
        requests.add(buildProductCustomersRequest(productId));

        return requests;
    }

    private MomiaHttpRequest buildProductRequest(long productId) {
        return MomiaHttpRequest.GET("product", true, baseServiceUrl("product", productId));
    }

    private MomiaHttpRequest buildProductCustomersRequest(long productId) {
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("start", 0)
                .add("count", conf.getInt("Product.CustomerPageSize"));

        return MomiaHttpRequest.GET("customers", false, baseServiceUrl("product", productId, "customer"), builder.build());
    }

    @RequestMapping(value = "/orderDetail.html", method = RequestMethod.GET)
    public ModelAndView getProductOrder(@RequestParam String utoken, @RequestParam long id) {
        if(StringUtils.isBlank(utoken) || id <= 0) return new ModelAndView("BadRequest", "msg","invalid params");

        List<MomiaHttpRequest> requests = buildProductOrderRequests(id, utoken);
        MomiaHttpResponseCollector collector = executeRequests(requests);
        if(!collector.isSuccessful())  return new ModelAndView("BadRequest", "msg","fail to get product");

        List list = new ArrayList();
        Map<String, Object> skus = buildSkus((JSONObject) collector.getResponse("contacts"), (JSONArray) collector.getResponse("skus"));
        list.add(skus);
      //  list.add(new ProductOrderFtl((JSONObject) collector.getResponse("contacts"), (JSONArray) collector.getResponse("skus")));

        return new ModelAndView("./product/sku", "skus", list);

    }
    private Map<String, Object> buildSkus(JSONObject contactsJson, JSONArray skusJson){
        Map<String, Object> skus = new HashMap<String, Object>();

        ProductOrderFtl productOrderFtl = new ProductOrderFtl(contactsJson, skusJson);
        skus.put("contacts", productOrderFtl.getContacts());
        skus.put("skus", productOrderFtl.getSkus());

        return skus;

    }

    private List<MomiaHttpRequest> buildProductOrderRequests(long productId, String utoken) {
        List<MomiaHttpRequest> requests = new ArrayList<MomiaHttpRequest>();
        requests.add(buildProductSkusRequest(productId));
        requests.add(buildUserRequest(utoken));

        return requests;
    }

    private MomiaHttpRequest buildProductSkusRequest(long productId) {
        return MomiaHttpRequest.GET("skus", true, baseServiceUrl("product", productId, "sku"));
    }

    private MomiaHttpRequest buildUserRequest(String utoken) {
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET("contacts", true, baseServiceUrl("user"), builder.build());

        return request;
    }

    @RequestMapping(value = "/playmate", method = RequestMethod.GET)
    public ModelAndView getProductPlaymates(@RequestParam long id) {
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("start", 0)
                .add("count", conf.getInt("Product.Playmate.MaxSkuCount"));
        MomiaHttpRequest request = MomiaHttpRequest.GET(baseServiceUrl("product", id, "playmate"), builder.build());
        List list = new ArrayList();
        JSONObject responseJson = new HttpExecute().getJsonObject(request);
        list.add(buildPlaymats((JSONArray) responseJson.get("data")));
        return new ModelAndView("./product/playmates", "playmates", list);
    }
    private Map<String, Object> buildPlaymats(JSONArray playmatesJson) {
        Map<String, Object> playmates = new HashMap<String, Object>();
        for(int i = 0; i<playmatesJson.size(); i++) {
            JSONObject parseJson = playmatesJson.getJSONObject(i);
            playmates.put("time", parseJson.getString("time"));
            playmates.put("joined", parseJson.getString("joined"));
            JSONArray jsonArray = parseJson.getJSONArray("playmates");
            List playmateInfo = new ArrayList();
            for(int j=0; j<jsonArray.size(); j++)
                playmateInfo.add(jsonArray.getJSONObject(j));
            playmates.put("playmates", playmateInfo);

        }

        return playmates;
    }


}

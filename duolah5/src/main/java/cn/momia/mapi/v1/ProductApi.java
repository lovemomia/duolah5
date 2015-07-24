package cn.momia.mapi.v1;

import cn.momia.common.web.http.MomiaHttpParamBuilder;
import cn.momia.common.web.http.MomiaHttpRequest;
import cn.momia.common.web.http.MomiaHttpResponseCollector;
import cn.momia.common.web.response.ResponseMessage;
import cn.momia.duolah5.common.HttpExecute;
import cn.momia.duolah5.dto.base.*;
import cn.momia.mapi.api.AbstractApi;
import cn.momia.mapi.dto.composite.PagedListDto;
import cn.momia.mapi.dto.misc.ProductUtil;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class ProductApi extends AbstractApi {
    private static final Logger LOGGER =  LoggerFactory.getLogger(ProductApi.class);

    @RequestMapping(value = "/weekend", method = RequestMethod.GET)
    public ModelAndView getProductsByWeekend(@RequestParam(value = "city") final int cityId, @RequestParam final int start) {
        final int pageSize = conf.getInt("Product.PageSize");
        final int maxPageCount = conf.getInt("Product.MaxPageCount");
        if (cityId < 0 || start < 0 || start > pageSize * maxPageCount) return new ModelAndView("BadRequest", "msg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("city", cityId)
                .add("start", start)
                .add("count", pageSize);
        MomiaHttpRequest request = MomiaHttpRequest.GET(baseServiceUrl("product/weekend"), builder.build());

        ResponseMessage responseMessage =  executeRequest(request, new Function<Object, Dto>() {
            @Override
            public Dto apply(Object data) {
                return buildProductsDtoOfWeekend((JSONObject) data, start, pageSize);
            }
        });
        List list = new ArrayList();
        list.add(responseMessage);
        return new ModelAndView("./product/products", "products", list);
    }

    private Dto buildProductsDtoOfWeekend(JSONObject productsPackJson, int start, int count) {
        PagedListDto products = new PagedListDto();

        long totalCount = productsPackJson.getLong("totalCount");
        products.setTotalCount(totalCount);
        if (start + count < totalCount) products.setNextIndex(start + count);

        JSONArray productsJson = productsPackJson.getJSONArray("products");
        products.addAll(ProductUtil.extractProductsData(productsJson));

        return products;
    }


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

        ResponseMessage responseMessage =  executeRequest(request, new Function<Object, Dto>() {
            @Override
            public Dto apply(Object data) {
                return buildProductsDto((JSONObject) data, start, pageSize);
            }
        });
        List list = new ArrayList();
        list.add(responseMessage.getData());
        return new ModelAndView("./product/products", "products", list);
    }

    private Dto buildProductsDto(JSONObject productsPackJson, int start, int count) {
        PagedListDto products = new PagedListDto();

        long totalCount = productsPackJson.getLong("totalCount");
        products.setTotalCount(totalCount);
        if (start + count < totalCount) products.setNextIndex(start + count);

        JSONArray productsJson = productsPackJson.getJSONArray("products");
        products.addAll(ProductUtil.extractProductsData(productsJson));

        return products;
    }

    @RequestMapping(value = "/actsDetail.html", method = RequestMethod.GET)
    public ModelAndView getProduct(@RequestParam final long id) {
        if (id <= 0) return new ModelAndView("BadRequest", "msg","invalid params");

        List<MomiaHttpRequest> requests = buildProductRequests(id);
        MomiaHttpResponseCollector collector = executeRequests(requests);

        ResponseMessage responseMessage =  executeRequests(requests, new Function<MomiaHttpResponseCollector, Dto>() {
            @Override
            public Dto apply(MomiaHttpResponseCollector collector) {
                return new ProductDetailFtl((JSONObject) collector.getResponse("product"), (JSONObject) collector.getResponse("customers"));
            }
        });
        List list = new ArrayList();
        list.add(responseMessage);
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
        ResponseMessage responseMessage =  executeRequest(request, new Function<Object, Dto>() {
            @Override
            public Dto apply(Object data) {
                return new PlaymatesFtl((JSONArray) data);
            }
        });
        List list = new ArrayList();
        list.add(responseMessage);
        return new ModelAndView("./product/playmates", "playmates", list);
    }



}

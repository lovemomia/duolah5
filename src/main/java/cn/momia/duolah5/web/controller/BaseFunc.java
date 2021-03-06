package cn.momia.duolah5.web.controller;

import cn.momia.duolah5.web.http.MomiaHttpParamBuilder;
import cn.momia.duolah5.web.http.MomiaHttpRequest;
import cn.momia.duolah5.web.img.ImageFile;
import cn.momia.duolah5.web.response.ResponseMessage;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ysm on 15-7-27.
 */
public class BaseFunc extends AbstractController {
    protected Function<Object, Object> userFunc = new Function<Object, Object>() {
        @Override
        public Object apply(Object data) {
            JSONObject userJson = (JSONObject) data;
            userJson.put("avatar", ImageFile.smallUrl(userJson.getString("avatar")));

            return data;
        }
    };

    protected Function<Object, Object> productFunc = new Function<Object, Object>() {
        @Override
        public Object apply(Object data) {
            JSONObject productJson = (JSONObject) data;
            productJson.put("url", buildUrl(productJson.getLong("id")));
            productJson.put("thumb", ImageFile.smallUrl(productJson.getString("thumb")));
            if (productJson.containsKey("cover")) productJson.put("cover", ImageFile.url(productJson.getString("cover")));
            if (productJson.containsKey("imgs")) productJson.put("imgs", processImgs(productJson.getJSONArray("imgs")));
            if (productJson.containsKey("content")) productJson.put("content", processContent(productJson.getJSONArray("content")));

            return data;
        }
    };

    private String buildUrl(long id) {
        return conf.getString("Product.Url") + "?id=" + id;
    }

    private static List<String> processImgs(JSONArray imgsJson) {
        List<String> imgs = new ArrayList<String>();
        for (int i = 0; i < imgsJson.size(); i++) {
            imgs.add(ImageFile.url(imgsJson.getString(i)));
        }

        return imgs;
    }

    private static JSONArray processContent(JSONArray contentJson) {
        for (int i = 0; i < contentJson.size(); i++) {
            JSONObject contentBlockJson = contentJson.getJSONObject(i);
            JSONArray bodyJson = contentBlockJson.getJSONArray("body");
            for (int j = 0; j < bodyJson.size(); j++) {
                JSONObject bodyBlockJson = bodyJson.getJSONObject(j);
                String img = bodyBlockJson.getString("img");
                if (!StringUtils.isBlank(img)) bodyBlockJson.put("img", ImageFile.url(img));
            }
        }

        return contentJson;
    }

    protected Function<Object, Object> productsFunc = new Function<Object, Object>() {
        @Override
        public Object apply(Object data) {
            JSONArray productsJson = (JSONArray) data;
            for (int i = 0; i < productsJson.size(); i++) {
                JSONObject productJson = productsJson.getJSONObject(i);
                productFunc.apply(productJson);
            }

            return data;
        }
    };

    protected Function<Object, Object> pagedProductsFunc = new Function<Object, Object>() {
        @Override
        public Object apply(Object data) {
            JSONArray productsJson = ((JSONObject) data).getJSONArray("list");
            productsFunc.apply(productsJson);

            return data;
        }
    };

    protected Function<Object, Object> orderFunc = new Function<Object, Object>() {
        @Override
        public Object apply(Object data) {
            JSONObject orderJson = (JSONObject) data;
            orderJson.put("cover", ImageFile.middleUrl(orderJson.getString("cover")));

            return data;
        }
    };

    protected long getUserId(String utoken) {
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(url("user"), builder.build());

        ResponseMessage response = executeRequest(request);
        if (response.successful()) return ((JSONObject) response.getData()).getLong("id");

        throw new RuntimeException("fail to get user id");
    }

    protected String getUserInviteCode(String utoken) {
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(url("user/code"), builder.build());

        ResponseMessage response = executeRequest(request);
        if (response.successful()) return (String) response.getData();

        throw new RuntimeException("fail to get user invite code");
    }

    protected String getUtoken(HttpServletRequest httpRequest) {
        Cookie[] cookies = httpRequest.getCookies();
        for(Cookie cookie : cookies)
            if(StringUtils.equals(cookie.getName(), "utoken")) {
               return cookie.getValue();
            }
        return "";

    }
}

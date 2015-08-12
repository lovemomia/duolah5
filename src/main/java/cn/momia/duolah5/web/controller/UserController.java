package cn.momia.duolah5.web.controller;

import cn.momia.duolah5.web.http.MomiaHttpParamBuilder;
import cn.momia.duolah5.web.http.MomiaHttpRequest;
import cn.momia.duolah5.web.img.ImageFile;
import cn.momia.duolah5.web.response.ResponseMessage;
import cn.momia.duolah5.ftl.base.OrderOfUserFtl;
import cn.momia.duolah5.ftl.base.ParticipantFtl;
import cn.momia.duolah5.ftl.composite.ListFtl;
import cn.momia.duolah5.ftl.composite.PagedListFtl;
import cn.momia.duolah5.ftl.base.Ftl;
import cn.momia.duolah5.ftl.base.UserFtl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
public class UserController extends BaseFunc {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    protected Function<Object, Object> orderDetailFunc = new Function<Object, Object>() {
        @Override
        public Object apply(Object data) {
            JSONObject orderDetailJson = (JSONObject) data;
            orderDetailJson.put("cover", ImageFile.url(orderDetailJson.getString("cover")));

            return data;
        }
    };

    protected Function<Object, Object> pagedOrdersFunc = new Function<Object, Object>() {
        @Override
        public Object apply(Object data) {
            JSONArray ordersJson = ((JSONObject) data).getJSONArray("list");
            for (int i = 0; i < ordersJson.size(); i++) {
                orderDetailFunc.apply(ordersJson.getJSONObject(i));
            }

            return data;
        }
    };
    @RequestMapping(value = "/profile")
    public ModelAndView profile() {
        return new ModelAndView("./user/profile");
    }
    @RequestMapping(value = "/profileInfo", method = RequestMethod.GET)
    public ModelAndView getUser(HttpServletRequest httpRequest) throws IOException {

        String utoken = getUtoken(httpRequest);
        if (StringUtils.isBlank(utoken))
            return new ModelAndView("BadRequest", "errmsg","invalid params");
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(url("user"), builder.build());

        ResponseMessage responseMessage = executeRequest(request, userFunc);
        List list = new ArrayList();
        list.add(responseMessage);
        return  new ModelAndView("./user/user","list",list);

    }
    private Map<String, Object> buildUser(JSONObject userJson) {
        Map<String, Object> user = new HashMap<String, Object>();
        if(!StringUtils.isBlank(userJson.get("data").toString())) {
            UserFtl userFtl = new UserFtl(userJson.getJSONObject("data"), false);
            user.put("user", userFtl);
        }
        else user.put("user", "");
        user.put("errno", userJson.getInteger("errno"));
        user.put("errmsg", userJson.getString("errmsg"));
        return user;
    }

    @RequestMapping(value = "/user_order", method = RequestMethod.GET)
    public ModelAndView getOrdersOfUser() {

        return new ModelAndView("./user/ordersOfUser", "list", "");
    }


    private Map<String, Object> buildChild(JSONObject childJson) {
        Map<String, Object> child = new HashMap<String, Object>();

        ParticipantFtl participantFtl = new ParticipantFtl(childJson,false);
        child.put("id", participantFtl.getId());
        child.put("name", participantFtl.getName());
        child.put("sex", participantFtl.getSex());
        child.put("birthday", participantFtl.getBirthday());

        return child;
    }


    private Ftl buildChildrenDto(JSONArray childrenJson) {
        ListFtl children = new ListFtl();
        for (int i = 0; i < childrenJson.size(); i++) {
            try {
                JSONObject childJson = childrenJson.getJSONObject(i);
                children.add(new ParticipantFtl(childJson));
            } catch (Exception e) {
                LOGGER.error("invalid child: {}", childrenJson);
            }
        }

        return children;
    }

    @RequestMapping(value = "/collect", method = RequestMethod.GET)
    public ModelAndView getFavoritesOfUser() {
        return new ModelAndView("./user/myfavorite", "list", "");
    }
}

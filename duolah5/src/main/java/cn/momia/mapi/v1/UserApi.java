package cn.momia.mapi.v1;

import cn.momia.common.web.http.MomiaHttpParamBuilder;
import cn.momia.common.web.http.MomiaHttpRequest;
import cn.momia.common.web.response.ResponseMessage;
import cn.momia.duolah5.common.HttpExecute;
import cn.momia.duolah5.dto.base.OrderOfUserFtl;
import cn.momia.duolah5.dto.base.ParticipantFtl;
import cn.momia.duolah5.dto.base.UserFtl;
import cn.momia.mapi.api.AbstractApi;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;

@Controller
public class UserApi  extends AbstractApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserApi.class);



    @RequestMapping(value = "/profile.html", method = RequestMethod.GET)
    public ModelAndView getUser(@RequestParam String utoken) throws IOException {
        if (StringUtils.isBlank(utoken))
            return new ModelAndView("BadRequest", "msg","invalid params");


        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(baseServiceUrl("user"), builder.build());

        JSONObject responseJson = new HttpExecute().getJsonObject(request);
        List list = new ArrayList();
        list.add(buildUser(responseJson));

        return  new ModelAndView("./user/user","list",list);

    }
    private Map<String, Object> buildUser(JSONObject userJson) {
        Map<String, Object> user = new HashMap<String, Object>();
        UserFtl userFtl = new UserFtl(userJson.getJSONObject("data"));
        user.put("avatar", userFtl.getAvatar());
        user.put("nickName", userFtl.getNickName());
        user.put("mobile", userFtl.getMobile());
        user.put("sex", userFtl.getSex());
        user.put("city", userFtl.getCity());
        user.put("children", userFtl.getChildren());
        return user;
    }

    @RequestMapping(value = "/user_order.html", method = RequestMethod.GET)
    public ModelAndView getOrdersOfUser(@RequestParam String utoken,
                                           @RequestParam int status,
                                           @RequestParam(defaultValue = "eq") String type,
                                           @RequestParam final int start,
                                           @RequestParam final int count) throws IOException {
        final int maxPageCount = conf.getInt("Order.MaxPageCount");
        final int pageSize = conf.getInt("Order.PageSize");
        if (StringUtils.isBlank(utoken) || start < 0 || start > pageSize * maxPageCount) return new ModelAndView("BadRequest", "msg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("status", status)
                .add("type", type)
                .add("start", start)
                .add("count", count);
        MomiaHttpRequest request = MomiaHttpRequest.GET(baseServiceUrl("user/order"), builder.build());

        JSONObject responseJson = new HttpExecute().getJsonObject(request);
        List list = new ArrayList();
        Map<String, Object> orders = buildOrdersDto(responseJson.getJSONObject("data"), start, count);
        list.add(orders);

        return new ModelAndView("./user/ordersOfUser", "list", list);


    }

    private Map<String, Object> buildOrdersDto(JSONObject ordersPackJson, int start, int count) {
        Map<String, Object>  orders= new HashMap<String, Object>();

        long totalCount = ordersPackJson.getLong("totalCount");
        orders.put("totalCount",totalCount);

        JSONArray ordersJson = ordersPackJson.getJSONArray("orders");
        List<OrderOfUserFtl> orderList = new ArrayList<OrderOfUserFtl>();
        for (int i = 0; i < ordersJson.size(); i++) {
            try {
                orderList.add(new OrderOfUserFtl(ordersJson.getJSONObject(i)));
            } catch (Exception e) {
                LOGGER.error("fail to parse order: {}", ordersJson.getJSONObject(i), e);
            }
        }
        orders.put("listOrder", orderList);
        if (start + count < totalCount) orders.put("nextIndex", start + count);


        return orders;
    }

    @RequestMapping(value = "/nickname", method = RequestMethod.POST)
    public ModelAndView updateNickName(@RequestParam String utoken, @RequestParam(value = "nickname") String nickName) throws IOException {
        if(StringUtils.isBlank(utoken) || StringUtils.isBlank(nickName)) return new ModelAndView("BadRequest", "msg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("nickname", nickName);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("user/nickname"), builder.build());

        JSONObject responseJson = new HttpExecute().getJsonObject(request);
        List list = new ArrayList();
        list.add(buildUser(responseJson));

        return  new ModelAndView("./user/user","list",list);

    }


    @RequestMapping(value = "/avatar", method = RequestMethod.POST)
    public ModelAndView updateAvatar(@RequestParam String utoken, @RequestParam String avatar) {
        if(StringUtils.isBlank(utoken) || StringUtils.isBlank(avatar)) return new ModelAndView("BadRequest", "msg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("avatar", avatar);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("user/avatar"), builder.build());


        JSONObject responseJson = new HttpExecute().getJsonObject(request);
        List list = new ArrayList();
        list.add(buildUser(responseJson));

        return  new ModelAndView("./user/user","list",list);

    }

    @RequestMapping(value = "/name", method = RequestMethod.POST)
    public ModelAndView updateName(@RequestParam String utoken, @RequestParam String name) {
        if(StringUtils.isBlank(utoken) || StringUtils.isBlank(name)) return new ModelAndView("BadRequest", "msg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("name", name);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("user/name"), builder.build());

        JSONObject responseJson = new HttpExecute().getJsonObject(request);
        List list = new ArrayList();
        list.add(buildUser(responseJson));

        return  new ModelAndView("./user/user","list",list);
    }

    @RequestMapping(value = "/sex", method = RequestMethod.POST)
    public ModelAndView updateSex(@RequestParam String utoken, @RequestParam String sex) {
        if(StringUtils.isBlank(utoken) || StringUtils.isBlank(sex)) return new ModelAndView("BadRequest", "msg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("sex", sex);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("user/sex"), builder.build());

        JSONObject responseJson = new HttpExecute().getJsonObject(request);
        List list = new ArrayList();
        list.add(buildUser(responseJson));

        return  new ModelAndView("./user/user","list",list);
    }

    @RequestMapping(value = "/birthday", method = RequestMethod.POST)
    public ModelAndView updateBirthday(@RequestParam String utoken, @RequestParam String birthday) {
        if(StringUtils.isBlank(utoken) || StringUtils.isBlank(birthday)) return new ModelAndView("BadRequest", "msg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("birthday", birthday);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("user/birthday"), builder.build());

        JSONObject responseJson = new HttpExecute().getJsonObject(request);
        List list = new ArrayList();
        list.add(buildUser(responseJson));

        return  new ModelAndView("./user/user","list",list);
    }

    @RequestMapping(value = "/city", method = RequestMethod.POST)
    public ModelAndView updateCity(@RequestParam String utoken, @RequestParam int city) {
        if(StringUtils.isBlank(utoken) || city <= 0) return new ModelAndView("BadRequest", "msg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("city", city);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("user/city"), builder.build());

        JSONObject responseJson = new HttpExecute().getJsonObject(request);
        List list = new ArrayList();
        list.add(buildUser(responseJson));

        return  new ModelAndView("./user/user","list",list);
    }

    @RequestMapping(value = "/address", method = RequestMethod.POST)
    public ModelAndView updateAddress(@RequestParam String utoken, @RequestParam String address) {
        if(StringUtils.isBlank(utoken) || StringUtils.isBlank(address)) return new ModelAndView("BadRequest", "msg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("address", address);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("user/address"), builder.build());

        JSONObject responseJson = new HttpExecute().getJsonObject(request);
        List list = new ArrayList();
        list.add(buildUser(responseJson));

        return  new ModelAndView("./user/user","list",list);
    }

    @RequestMapping(value = "/child", method = RequestMethod.POST)
    public ModelAndView addChild(@RequestParam String utoken, @RequestParam String children) {
        if(StringUtils.isBlank(utoken) || StringUtils.isBlank(children)) return new ModelAndView("BadRequest", "msg","invalid params");

        long userId = getUserId(utoken);
        if (userId <= 0) return new ModelAndView("user does not exist");

        JSONArray childrenJson = JSONArray.parseArray(children);
        for (int i = 0; i < childrenJson.size(); i++) childrenJson.getJSONObject(i).put("userId", userId);
        MomiaHttpRequest request = MomiaHttpRequest.POST(baseServiceUrl("user/child"), childrenJson.toString());

        JSONObject responseJson = new HttpExecute().getJsonObject(request);

        List list = new ArrayList();
        list.add(buildUser(responseJson));

        return new ModelAndView("./user/user", "list", list);
    }

    @RequestMapping(value = "/child/name", method = RequestMethod.POST)
    public ModelAndView updateChildByName(@RequestParam String utoken,
                                             @RequestParam(value = "cid") long childId,
                                             @RequestParam String name) {
        if (StringUtils.isBlank(utoken) || childId <= 0 || StringUtils.isBlank(name)) return new ModelAndView("BadRequest", "msg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("id", childId)
                .add("name", name);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("participant/name"), builder.build());

        ResponseMessage response = executeRequest(request);
        if (!response.successful()) return new ModelAndView(response.getErrmsg());

        JSONObject responseJson = new HttpExecute().getJsonObject(MomiaHttpRequest.GET(baseServiceUrl("user"), new MomiaHttpParamBuilder().add("utoken", utoken).build()));

        List list = new ArrayList();
        list.add(buildUser(responseJson));

        return new ModelAndView("./user/user", "list", list);
    }

    @RequestMapping(value = "/child/sex", method = RequestMethod.POST)
    public ModelAndView updateChildBySex(@RequestParam String utoken,
                                            @RequestParam(value = "cid") long childId,
                                            @RequestParam String sex) {
        if (StringUtils.isBlank(utoken) || childId <= 0 || StringUtils.isBlank(sex)) return new ModelAndView("BadRequest", "msg","invalid params");
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("id", childId)
                .add("sex", sex);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("participant/sex"), builder.build());

        ResponseMessage response = executeRequest(request);
        if (!response.successful()) new ModelAndView(response.getErrmsg());

        JSONObject responseJson = new HttpExecute().getJsonObject(MomiaHttpRequest.GET(baseServiceUrl("user"), new MomiaHttpParamBuilder().add("utoken", utoken).build()));

        List list = new ArrayList();
        list.add(buildUser(responseJson));

        return new ModelAndView("./user/user", "list", list);
    }

    @RequestMapping(value = "/child/birthday", method = RequestMethod.POST)
    public ModelAndView updateChildByBirthday(@RequestParam String utoken,
                                                 @RequestParam(value = "cid") long childId,
                                                 @RequestParam String birthday) {
        if (StringUtils.isBlank(utoken) || childId <= 0 || StringUtils.isBlank(birthday)) return new ModelAndView("BadRequest", "msg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("id", childId)
                .add("birthday", birthday);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("participant/birthday"), builder.build());

        ResponseMessage response = executeRequest(request);
        if (!response.successful()) return new ModelAndView(response.getErrmsg());

        JSONObject responseJson = new HttpExecute().getJsonObject(MomiaHttpRequest.GET(baseServiceUrl("user"),
                new MomiaHttpParamBuilder().add("utoken", utoken).build()));
        List list = new ArrayList();
        list.add(buildUser(responseJson));

        return new ModelAndView("./user/user", "list", list);
    }

    @RequestMapping(value = "/child/delete", method = RequestMethod.POST)
    public ModelAndView deleteChild(@RequestParam String utoken, @RequestParam(value = "cid") long childId) {
        if(StringUtils.isBlank(utoken) || childId <= 0)  return new ModelAndView("BadRequest", "msg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.DELETE(baseServiceUrl("user/child", childId), builder.build());

       JSONObject responseJson = new HttpExecute().getJsonObject(request);
        List list = new ArrayList();
        list.add(buildUser(responseJson));

        return  new ModelAndView("./user/user","list",list);
    }

    @RequestMapping(value = "/child", method = RequestMethod.GET)
    public ModelAndView getChild(@RequestParam String utoken, @RequestParam(value = "cid") long childId) {
        if(StringUtils.isBlank(utoken) || childId <= 0) return new ModelAndView("BadRequest", "msg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(baseServiceUrl("user/child", childId), builder.build());

        JSONObject responseJson = new HttpExecute().getJsonObject(request);
        List list = new ArrayList();
        list.add(buildChild(responseJson.getJSONObject("data")));

        return  new ModelAndView("./user/children","list",list);
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

    @RequestMapping(value = "/child/list", method = RequestMethod.GET)
    public ModelAndView getChildren(@RequestParam String utoken) {
        if(StringUtils.isBlank(utoken)) return new ModelAndView("BadRequest", "msg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(baseServiceUrl("user/child"), builder.build());

        JSONObject responseJson = new HttpExecute().getJsonObject(request);
        return new ModelAndView("./user/children","list",buildChildren(responseJson));

    }

    private List<Map<String, Object>> buildChildren(JSONObject responseJson) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        JSONArray children = responseJson.getJSONArray("data");
        for(int i=0; i<children.size(); i++){
            list.add(buildChild(children.getJSONObject(i)));
        }
        return list;
    }

}

package cn.momia.mapi.v1;

import cn.momia.common.web.http.MomiaHttpParamBuilder;
import cn.momia.common.web.http.MomiaHttpRequest;
import cn.momia.common.web.response.ResponseMessage;
import cn.momia.duolah5.common.HttpExecute;
import cn.momia.duolah5.dto.base.Dto;
import cn.momia.duolah5.dto.base.OrderOfUserFtl;
import cn.momia.duolah5.dto.base.ParticipantFtl;
import cn.momia.duolah5.dto.base.UserFtl;
import cn.momia.mapi.api.AbstractApi;
import cn.momia.mapi.dto.composite.ListDto;
import cn.momia.mapi.dto.composite.PagedListDto;
import com.alibaba.fastjson.JSON;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;

@Controller
public class UserApi  extends AbstractApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserApi.class);

    private static final Function<Object, Dto> userFunc = new Function<Object, Dto>() {
        @Override
        public Dto apply(Object data) {
            return new UserFtl((JSONObject) data);
        }
    };

    @RequestMapping(value = "/profile.html", method = RequestMethod.GET)
    public ModelAndView getUser(@RequestParam String utoken) throws IOException {
        if (StringUtils.isBlank(utoken))
            return new ModelAndView("BadRequest", "errmsg","invalid params");


        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(baseServiceUrl("user"), builder.build());

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

    @RequestMapping(value = "/user_order.html", method = RequestMethod.GET)
    public ModelAndView getOrdersOfUser(@RequestParam String utoken,
                                           @RequestParam int status,
                                           @RequestParam(defaultValue = "eq") String type,
                                           @RequestParam final int start,
                                           @RequestParam final int count) throws IOException {
        final int maxPageCount = conf.getInt("Order.MaxPageCount");
        final int pageSize = conf.getInt("Order.PageSize");
        if (StringUtils.isBlank(utoken) || start < 0 || start > pageSize * maxPageCount) return new ModelAndView("BadRequest", "errmsg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("status", status)
                .add("type", type)
                .add("start", start)
                .add("count", count);
        MomiaHttpRequest request = MomiaHttpRequest.GET(baseServiceUrl("user/order"), builder.build());

        ResponseMessage responseMessage = executeRequest(request, new Function<Object, Dto>() {
            @Override
            public Dto apply(Object data) {
                return buildOrdersDto((JSONObject) data, start, pageSize);
            }
        });
        return new ModelAndView("./user/ordersOfUser", "list", responseMessage);


    }

    private Dto buildOrdersDto(JSONObject ordersPackJson, int start, int count) {
        PagedListDto orders = new PagedListDto();

        long totalCount = ordersPackJson.getLong("totalCount");
        orders.setTotalCount(totalCount);

        JSONArray ordersJson = ordersPackJson.getJSONArray("orders");
        for (int i = 0; i < ordersJson.size(); i++) {
            try {
                orders.add(new OrderOfUserFtl(ordersJson.getJSONObject(i), true));
            } catch (Exception e) {
                LOGGER.error("fail to parse order: {}", ordersJson.getJSONObject(i), e);
            }
        }
        if (start + count < totalCount) orders.setNextIndex(start + count);

        return orders;
    }

    @RequestMapping(value = "/nickname", method = RequestMethod.POST)
    public ModelAndView updateNickName(@RequestParam String utoken, @RequestParam(value = "nickname") String nickName) throws IOException {
        if(StringUtils.isBlank(utoken) || StringUtils.isBlank(nickName)) return new ModelAndView("BadRequest", "errmsg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("nickname", nickName);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("user/nickname"), builder.build());

        ResponseMessage responseMessage = executeRequest(request, userFunc);
        List list = new ArrayList();
        list.add(responseMessage);

        return  new ModelAndView("./user/user","list",list);

    }


    @RequestMapping(value = "/avatar", method = RequestMethod.POST)
    public ModelAndView updateAvatar(@RequestParam String utoken, @RequestParam String avatar) {
        if(StringUtils.isBlank(utoken) || StringUtils.isBlank(avatar)) return new ModelAndView("BadRequest", "errmsg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("avatar", avatar);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("user/avatar"), builder.build());


        ResponseMessage responseMessage = executeRequest(request, userFunc);
        List list = new ArrayList();
        list.add(responseMessage);

        return  new ModelAndView("./user/user","list",list);


    }

    @RequestMapping(value = "/name", method = RequestMethod.POST)
    public ModelAndView updateName(@RequestParam String utoken, @RequestParam String name) {
        if(StringUtils.isBlank(utoken) || StringUtils.isBlank(name)) return new ModelAndView("BadRequest", "errmsg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("name", name);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("user/name"), builder.build());

        ResponseMessage responseMessage = executeRequest(request, userFunc);
        List list = new ArrayList();
        list.add(responseMessage);

        return  new ModelAndView("./user/user","list",list);

    }

    @RequestMapping(value = "/sex", method = RequestMethod.POST)
    public ModelAndView updateSex(@RequestParam String utoken, @RequestParam String sex) {
        if(StringUtils.isBlank(utoken) || StringUtils.isBlank(sex)) return new ModelAndView("BadRequest", "errmsg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("sex", sex);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("user/sex"), builder.build());

        ResponseMessage responseMessage = executeRequest(request, userFunc);
        List list = new ArrayList();
        list.add(responseMessage);

        return  new ModelAndView("./user/user","list",list);

    }

    @RequestMapping(value = "/birthday", method = RequestMethod.POST)
    public ModelAndView updateBirthday(@RequestParam String utoken, @RequestParam String birthday) {
        if(StringUtils.isBlank(utoken) || StringUtils.isBlank(birthday)) return new ModelAndView("BadRequest", "errmsg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("birthday", birthday);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("user/birthday"), builder.build());

        ResponseMessage responseMessage = executeRequest(request, userFunc);
        List list = new ArrayList();
        list.add(responseMessage);

        return  new ModelAndView("./user/user","list",list);

    }

    @RequestMapping(value = "/city", method = RequestMethod.POST)
    public ModelAndView updateCity(@RequestParam String utoken, @RequestParam int city) {
        if(StringUtils.isBlank(utoken) || city <= 0) return new ModelAndView("BadRequest", "errmsg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("city", city);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("user/city"), builder.build());

        ResponseMessage responseMessage = executeRequest(request, userFunc);
        List list = new ArrayList();
        list.add(responseMessage);

        return  new ModelAndView("./user/user","list",list);

    }

    @RequestMapping(value = "/address", method = RequestMethod.POST)
    public ModelAndView updateAddress(@RequestParam String utoken, @RequestParam String address) {
        if(StringUtils.isBlank(utoken) || StringUtils.isBlank(address)) return new ModelAndView("BadRequest", "errmsg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("address", address);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("user/address"), builder.build());

        ResponseMessage responseMessage = executeRequest(request, userFunc);
        List list = new ArrayList();
        list.add(responseMessage);

        return  new ModelAndView("./user/user","list",list);

    }

    @RequestMapping(value = "/child", method = RequestMethod.POST)
    public ModelAndView addChild(@RequestParam String utoken, @RequestParam String children) {
        if(StringUtils.isBlank(utoken) || StringUtils.isBlank(children)) return new ModelAndView("BadRequest", "errmsg","invalid params");

        long userId = getUserId(utoken);
        if (userId <= 0) return new ModelAndView("user does not exist");

        JSONArray childrenJson = JSONArray.parseArray(children);
        for (int i = 0; i < childrenJson.size(); i++) childrenJson.getJSONObject(i).put("userId", userId);
        MomiaHttpRequest request = MomiaHttpRequest.POST(baseServiceUrl("user/child"), childrenJson.toString());

        ResponseMessage responseMessage = executeRequest(request, userFunc);
        List list = new ArrayList();
        list.add(responseMessage);

        return  new ModelAndView("./user/user","list",list);

    }

    @RequestMapping(value = "/child/name", method = RequestMethod.POST)
    public ModelAndView updateChildByName(@RequestParam String utoken,
                                             @RequestParam(value = "cid") long childId,
                                             @RequestParam String name) {
        if (StringUtils.isBlank(utoken) || childId <= 0 || StringUtils.isBlank(name)) return new ModelAndView("BadRequest", "errmsg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("id", childId)
                .add("name", name);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("participant/name"), builder.build());

        ResponseMessage response = executeRequest(request);
        if (!response.successful()) return new ModelAndView("BadRequest", "errmsg", response.getErrmsg());

        ResponseMessage responseMessage =  executeRequest(MomiaHttpRequest.GET(baseServiceUrl("user"), new MomiaHttpParamBuilder().add("utoken", utoken).build()), userFunc);
        List list = new ArrayList();
        list.add(responseMessage);

        return  new ModelAndView("./user/user","list",list);
    }


    @RequestMapping(value = "/child/sex", method = RequestMethod.POST)
    public ModelAndView updateChildBySex(@RequestParam String utoken,
                                            @RequestParam(value = "cid") long childId,
                                            @RequestParam String sex) {
        if (StringUtils.isBlank(utoken) || childId <= 0 || StringUtils.isBlank(sex)) return new ModelAndView("BadRequest", "errmsg","invalid params");
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("id", childId)
                .add("sex", sex);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("participant/sex"), builder.build());

        ResponseMessage response = executeRequest(request);
        if (!response.successful()) return new ModelAndView("BadRequest", "errmsg", response.getErrmsg());

        ResponseMessage responseMessage =  executeRequest(MomiaHttpRequest.GET(baseServiceUrl("user"), new MomiaHttpParamBuilder().add("utoken", utoken).build()), userFunc);
        List list = new ArrayList();
        list.add(responseMessage);

        return  new ModelAndView("./user/user","list",list);

    }

    @RequestMapping(value = "/child/birthday", method = RequestMethod.POST)
    public ModelAndView updateChildByBirthday(@RequestParam String utoken,
                                                 @RequestParam(value = "cid") long childId,
                                                 @RequestParam String birthday) {
        if (StringUtils.isBlank(utoken) || childId <= 0 || StringUtils.isBlank(birthday)) return new ModelAndView("BadRequest", "errmsg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("id", childId)
                .add("birthday", birthday);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("participant/birthday"), builder.build());

        ResponseMessage response = executeRequest(request);
        if (!response.successful()) return new ModelAndView("BadRequest", "errmsg", response.getErrmsg());

        ResponseMessage responseMessage =  executeRequest(MomiaHttpRequest.GET(baseServiceUrl("user"), new MomiaHttpParamBuilder().add("utoken", utoken).build()), userFunc);
        List list = new ArrayList();
        list.add(responseMessage);

        return  new ModelAndView("./user/user","list",list);

    }

    @RequestMapping(value = "/child/delete", method = RequestMethod.POST)
    public ModelAndView deleteChild(@RequestParam String utoken, @RequestParam(value = "cid") long childId) {
        if(StringUtils.isBlank(utoken) || childId <= 0)  return new ModelAndView("BadRequest", "errmsg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.DELETE(baseServiceUrl("user/child", childId), builder.build());

        ResponseMessage response = executeRequest(request);
        if (!response.successful()) return new ModelAndView("BadRequest", "errmsg", response.getErrmsg());

        ResponseMessage responseMessage =  executeRequest(MomiaHttpRequest.GET(baseServiceUrl("user"), new MomiaHttpParamBuilder().add("utoken", utoken).build()), userFunc);
        List list = new ArrayList();
        list.add(responseMessage);

        return  new ModelAndView("./user/user","list",list);

    }

    @RequestMapping(value = "/child", method = RequestMethod.GET)
    public ModelAndView getChild(@RequestParam String utoken, @RequestParam(value = "cid") long childId) {
        if(StringUtils.isBlank(utoken) || childId <= 0) return new ModelAndView("BadRequest", "errmsg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(baseServiceUrl("user/child", childId), builder.build());

        ResponseMessage responseMessage = executeRequest(request, new Function<Object, Dto>() {
            @Override
            public Dto apply(Object data) {
                return new ParticipantFtl((JSONObject) data);
            }
        });
        List list = new ArrayList();
        list.add(responseMessage);

        return  new ModelAndView("./user/participant","participant",list);

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
        if(StringUtils.isBlank(utoken)) return new ModelAndView("BadRequest", "errmsg","invalid params");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(baseServiceUrl("user/child"), builder.build());

        ResponseMessage responseMessage = executeRequest(request, new Function<Object, Dto>() {
            @Override
            public Dto apply(Object data) {
                return buildChildrenDto((JSONArray) data);
            }
        });
        List list = new ArrayList();
        list.add(responseMessage);

        return  new ModelAndView("./user/children","list",list);

    }

    private Dto buildChildrenDto(JSONArray childrenJson) {
        ListDto children = new ListDto();
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

}

package cn.momia.mapi.v1;

import cn.momia.common.web.http.MomiaHttpParamBuilder;
import cn.momia.common.web.http.MomiaHttpRequest;
import cn.momia.common.web.response.ResponseMessage;
import cn.momia.duolah5.common.HttpExecute;
import cn.momia.duolah5.dto.base.ParticipantFtl;
import cn.momia.mapi.api.AbstractApi;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/com_outer.html")
public class ParticipantApi extends AbstractApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantApi.class);

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView addParticipant(@RequestParam String utoken, @RequestParam String participant) {


        long userId = getUserId(utoken);
        if (userId <= 0) return new ModelAndView("BadRequest", "msg","user token expired");

        JSONObject paticipantJson = JSON.parseObject(participant);
        paticipantJson.put("userId", userId);
        MomiaHttpRequest request = MomiaHttpRequest.POST(baseServiceUrl("participant"), paticipantJson.toString());
        JSONObject parseJson = new HttpExecute().getJsonObject(request);
        return new ModelAndView("success", "isSuccessful", parseJson);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getParticipant(@RequestParam String utoken, @RequestParam long id) {
        if (StringUtils.isBlank(utoken) || id <= 0) return new ModelAndView("success", "msg", "invalid param");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(baseServiceUrl("participant", id), builder.build());
        JSONObject parseJson = new HttpExecute().getJsonObject(request);
        List list = new ArrayList();
        list.add( new ParticipantFtl(parseJson, true));
        return new ModelAndView("./user/participant", "participant",  list);

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateParticipant(@RequestParam String utoken, @RequestParam String participant) {
        if (StringUtils.isBlank(utoken) || StringUtils.isBlank(participant)) return new ModelAndView("success", "msg", "invalid param");

        long userId = getUserId(utoken);
        if (userId <= 0) return new ModelAndView("success", "msg", "user token expried");

        JSONObject paticipantJson = JSON.parseObject(participant);
        paticipantJson.put("userId", userId);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("participant"), paticipantJson.toString());
        JSONObject jsonObject = new HttpExecute().getJsonObject(request);
        return new ModelAndView("success", "isSuccessful", jsonObject);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView getParticipantsOfUser(@RequestParam String utoken) {
        if (StringUtils.isBlank(utoken)) return new ModelAndView("success", "msg", "invalid param");
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(baseServiceUrl("participant"), builder.build());
        JSONObject parseJson = new HttpExecute().getJsonObject(request);
        JSONArray participantsJson = parseJson.getJSONArray("data");
        List list = new ArrayList();
        for (int i = 0; i < participantsJson.size(); i++) {
            try {
                JSONObject participantJson = participantsJson.getJSONObject(i);
                list.add(new ParticipantFtl(participantJson,false));
            } catch (Exception e) {
                LOGGER.error("invalid participant: {}", participantsJson.get(i));
            }
        }
        return new ModelAndView("./user/participant", "participant",  list);


    }


}

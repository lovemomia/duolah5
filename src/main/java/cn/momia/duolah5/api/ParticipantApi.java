package cn.momia.duolah5.api;


import cn.momia.common.web.http.MomiaHttpParamBuilder;
import cn.momia.common.web.http.MomiaHttpRequest;
import cn.momia.common.web.response.ResponseMessage;
import cn.momia.dto.base.Dto;

import cn.momia.dto.base.ParticipantFtl;
import cn.momia.duolah5.dto.composite.ListDto;
import com.alibaba.fastjson.JSON;
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
import java.util.List;

@Controller
@RequestMapping
public class ParticipantApi extends BaseFunc {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantApi.class);

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addParticipant(@RequestParam String utoken, @RequestParam String participant) {


        long userId = getUserId(utoken);
        if (userId <= 0) return new ModelAndView("BadRequest", "msg","user token expired");
        JSONObject paticipantJson = JSON.parseObject(participant);
        paticipantJson.put("userId", userId);
        MomiaHttpRequest request = MomiaHttpRequest.POST(url("participant"), paticipantJson.toString());
        ResponseMessage responseMessage = executeRequest(request);
        List list = new ArrayList();
        list.add(responseMessage);
        return new ModelAndView("success", "isSuccessful", list);
    }

    @RequestMapping(value = "/edit_com_outer.html", method = RequestMethod.GET)
    public ModelAndView getParticipant(@RequestParam String utoken, @RequestParam long id) {
        if (StringUtils.isBlank(utoken) || id <= 0) return new ModelAndView("success", "msg", "invalid param");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(url("participant", id), builder.build());
        ResponseMessage responseMessage =  executeRequest(request);
        List list = new ArrayList();
        list.add(responseMessage);
        return new ModelAndView("./user/participant", "participant", list);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateParticipant(@RequestParam String utoken, @RequestParam String participant) {
        if (StringUtils.isBlank(utoken) || StringUtils.isBlank(participant)) return new ModelAndView("success", "msg", "invalid param");

        long userId = getUserId(utoken);
        if (userId <= 0) return new ModelAndView("success", "msg", "user token expried");

        JSONObject paticipantJson = JSON.parseObject(participant);
        paticipantJson.put("userId", userId);
        MomiaHttpRequest request = MomiaHttpRequest.PUT(url("participant"), paticipantJson.toString());
        ResponseMessage responseMessage = executeRequest(request);
        List list = new ArrayList();
        list.add(responseMessage);
        return new ModelAndView("success", "isSuccessful", list);
    }

    @RequestMapping(value = "/com_outer.html", method = RequestMethod.GET)
    public ModelAndView getParticipantsOfUser(@RequestParam String utoken) {
        if (StringUtils.isBlank(utoken)) return new ModelAndView("success", "msg", "invalid param");
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(url("participant"), builder.build());
        ResponseMessage responseMessage =  executeRequest(request);
        List list = new ArrayList();
        list.add(responseMessage);
        return new ModelAndView("./user/children", "list", list);
    }

    private Dto buildParticipantsDto(JSONArray participantsJson) {
        ListDto participants = new ListDto();
        for (int i = 0; i < participantsJson.size(); i++) {
            try {
                JSONObject participantJson = participantsJson.getJSONObject(i);
                participants.add(new ParticipantFtl(participantJson));
            } catch (Exception e) {
                LOGGER.error("invalid participant: {}", participantsJson.get(i));
            }
        }

        return participants;
    }


}

package cn.momia.mapi.v1;

import cn.momia.common.web.http.MomiaHttpParamBuilder;
import cn.momia.common.web.http.MomiaHttpRequest;
import cn.momia.common.web.response.ResponseMessage;
import cn.momia.duolah5.common.HttpExecute;
import cn.momia.duolah5.dto.base.Dto;
import cn.momia.duolah5.dto.base.ParticipantFtl;
import cn.momia.mapi.api.AbstractApi;
import cn.momia.mapi.dto.composite.ListDto;
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
        ResponseMessage responseMessage = executeRequest(request);
        List list = new ArrayList();
        list.add(responseMessage);
        return new ModelAndView("success", "isSuccessful", list);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getParticipant(@RequestParam String utoken, @RequestParam long id) {
        if (StringUtils.isBlank(utoken) || id <= 0) return new ModelAndView("success", "msg", "invalid param");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(baseServiceUrl("participant", id), builder.build());
        ResponseMessage responseMessage =  executeRequest(request, new Function<Object, Dto>() {
            @Override
            public Dto apply(Object data) {
                return new ParticipantFtl((JSONObject) data, true);
            }
        });
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
        MomiaHttpRequest request = MomiaHttpRequest.PUT(baseServiceUrl("participant"), paticipantJson.toString());
        ResponseMessage responseMessage = executeRequest(request);
        List list = new ArrayList();
        list.add(responseMessage);
        return new ModelAndView("success", "isSuccessful", list);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView getParticipantsOfUser(@RequestParam String utoken) {
        if (StringUtils.isBlank(utoken)) return new ModelAndView("success", "msg", "invalid param");
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(baseServiceUrl("participant"), builder.build());
        ResponseMessage responseMessage =  executeRequest(request, new Function<Object, Dto>() {
            @Override
            public Dto apply(Object data) {
                return buildParticipantsDto((JSONArray) data);
            }
        });
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

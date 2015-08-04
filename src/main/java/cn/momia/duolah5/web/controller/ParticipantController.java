package cn.momia.duolah5.web.controller;


import cn.momia.duolah5.web.http.MomiaHttpParamBuilder;
import cn.momia.duolah5.web.http.MomiaHttpRequest;
import cn.momia.duolah5.web.response.ResponseMessage;
import cn.momia.duolah5.ftl.base.Ftl;

import cn.momia.duolah5.ftl.base.ParticipantFtl;
import cn.momia.duolah5.ftl.composite.ListFtl;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class ParticipantController extends BaseFunc {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantController.class);


    @RequestMapping(value = "/edit_com_outer.html", method = RequestMethod.GET)
    public ModelAndView getParticipant(HttpServletRequest httpRequest, @RequestParam long id) {
        String utoken = getUtoken(httpRequest);
        if (StringUtils.isBlank(utoken) || id <= 0) return new ModelAndView("success", "msg", "invalid param");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(url("participant", id), builder.build());
        ResponseMessage responseMessage =  executeRequest(request);
        if(responseMessage.getErrno() != 0)
            return new ModelAndView("BadRequest", "errmsg", "error!");
        List list = new ArrayList();
        list.add(responseMessage.getData());
        return new ModelAndView("./user/participant", "participant", list);
    }

    @RequestMapping(value = "/com_outer.html", method = RequestMethod.GET)
    public ModelAndView getParticipantsOfUser(HttpServletRequest httpRequest) {
        String utoken = getUtoken(httpRequest);

        if (StringUtils.isBlank(utoken)) return new ModelAndView("success", "msg", "invalid param");
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(url("participant"), builder.build());
        ResponseMessage responseMessage =  executeRequest(request);
        if(responseMessage.getErrno() != 0 )
            return new ModelAndView("BadRequest", "errmsg", "error!");

        List list = new ArrayList();
        list.add(responseMessage.getData());
        return new ModelAndView("./user/participant", "participant", list);
    }

    private Ftl buildParticipantsDto(JSONArray participantsJson) {
        ListFtl participants = new ListFtl();
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

package cn.momia.duolah5.web.controller;


import cn.momia.duolah5.web.http.MomiaHttpParamBuilder;
import cn.momia.duolah5.web.http.MomiaHttpRequest;
import cn.momia.duolah5.web.response.ResponseMessage;

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


    @RequestMapping(value = "/edit_outer.html", method = RequestMethod.GET)
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
        return new ModelAndView("./user/participant/get_participant", "participant", list);
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
        return new ModelAndView("./user/participant/participant", "participant", list);
    }

    @RequestMapping(value = "/addOuter.html")
    public ModelAndView addParticipant() {
        return new ModelAndView("./user/participant/addParticipant");
    }


}

package cn.momia.duolah5.web.controller;

import cn.momia.duolah5.web.http.MomiaHttpParamBuilder;
import cn.momia.duolah5.web.http.MomiaHttpRequest;
import cn.momia.duolah5.web.response.ResponseMessage;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseFunc {
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView detail(@RequestParam(value = "oid") long orderId,
                               @RequestParam(value ="pid") long productId,
                               HttpServletRequest request) {
        String utoken = getUtoken(request);
        if (StringUtils.isBlank(utoken)) return new ModelAndView("BadRequest", "errmsg", "error");

        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder()
                .add("utoken", utoken)
                .add("pid", productId);
        MomiaHttpRequest momiaHttpRequest = MomiaHttpRequest.GET(url("order", orderId), builder.build());

        ResponseMessage responseMessage = executeRequest(momiaHttpRequest, orderFunc);
        if (!responseMessage.successful()) return new ModelAndView("BadRequest", "errmsg", "error");

        return new ModelAndView("./order/detail", "order", responseMessage.getData());
    }
}

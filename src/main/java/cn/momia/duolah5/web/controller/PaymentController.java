package cn.momia.duolah5.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ysm on 15-8-7.
 */
@Controller
public class PaymentController extends BaseFunc {
    @RequestMapping(value = "/orderPay.html")
    public ModelAndView payment() {
        return new ModelAndView("./payment/payment");
    }

    @RequestMapping(value = "/payOk.html")
    public ModelAndView payOk() {
        return new ModelAndView("./payment/payOk");
    }
}

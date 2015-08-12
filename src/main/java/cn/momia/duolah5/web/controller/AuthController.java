package cn.momia.duolah5.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ysm on 15-8-7.
 */
@Controller
public class AuthController extends BaseFunc {

    @RequestMapping(value = "/registerpsw")
    public ModelAndView register() {
        return new ModelAndView("./auth/register");
    }

    @RequestMapping(value = "/loginpsw")
    public ModelAndView login() {
        return new ModelAndView("./auth/login");

    }

    @RequestMapping(value = "/login")
    public ModelAndView loginWithCode() {
        return new ModelAndView("./auth/loginWithCode");
    }

    @RequestMapping(value = "/resetpsw")
    public ModelAndView fogetPassword() {
        return new ModelAndView("./auth/forgetPassword");
    }
}

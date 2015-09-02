package cn.momia.duolah5.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by ysm on 15-8-7.
 */
@Controller
public class DownloadController extends BaseFunc {
    @RequestMapping(value = "/download")
    public ModelAndView downloadApp() {
        return new ModelAndView("/download");
    }
}

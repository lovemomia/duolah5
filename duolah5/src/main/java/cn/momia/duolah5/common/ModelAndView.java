package cn.momia.duolah5.common;

import cn.momia.common.web.response.ErrorCode;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ysm on 15-7-20.
 */
public class ModelAndView implements Serializable{
    public static final ModelAndView SUCCESS = new ModelAndView("success");
    public static final ModelAndView FAILED = new ModelAndView(ErrorCode.FAILED, "failed");
    public static final ModelAndView BAD_REQUEST = new ModelAndView(ErrorCode.BAD_REQUEST, "invalid params");
    public static final ModelAndView TOKEN_EXPIRED = new ModelAndView(ErrorCode.TOKEN_EXPIRED, "user token expired");
    public static final ModelAndView EMPTY_MAP = new ModelAndView(new JSONObject());
    public static final ModelAndView EMPTY_ARRAY = new ModelAndView(new JSONArray());

    public static ModelAndView FAILED(String errmsg) {
        return new ModelAndView(ErrorCode.FAILED, errmsg);
    }

    public static ModelAndView formJson(JSONObject jsonObject) {
        ModelAndView responseMessage = new ModelAndView();
        responseMessage.errno = jsonObject.getInteger("errno");
        responseMessage.errmsg = jsonObject.getString("errmsg");
        responseMessage.data = jsonObject.get("data");

        return responseMessage;
    }

    private int errno = ErrorCode.FAILED;
    private String errmsg;
    private Object data = "";
    private long time = new Date().getTime();

    private ModelAndView() {

    }

    public ModelAndView(int errno, String errmsg)
    {
        this.errno = errno;
        this.errmsg = errmsg;
    }

    public ModelAndView(Object data)
    {
        this(ErrorCode.SUCCESS, "success");
        this.data = data;
    }

    public int getErrno()
    {
        return errno;
    }

    public void setErrno(int errno)
    {
        this.errno = errno;
    }

    public String getErrmsg()
    {
        return errmsg;
    }

    public void setErrmsg(String errmsg)
    {
        this.errmsg = errmsg;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public boolean successful() {
        return errno == ErrorCode.SUCCESS;
    }
}

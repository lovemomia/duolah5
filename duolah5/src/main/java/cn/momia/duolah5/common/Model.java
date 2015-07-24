package cn.momia.duolah5.common;

import cn.momia.common.web.response.ErrorCode;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ysm on 15-7-20.
 */
public class Model extends ModelAndView implements Serializable{
    public static final Model SUCCESS = new Model("success");
    public static final Model FAILED = new Model(ErrorCode.FAILED, "failed");
    public static final Model BAD_REQUEST = new Model(ErrorCode.BAD_REQUEST, "invalid params");
    public static final Model TOKEN_EXPIRED = new Model(ErrorCode.TOKEN_EXPIRED, "user token expired");
    public static final Model EMPTY_MAP = new Model(new JSONObject());
    public static final Model EMPTY_ARRAY = new Model(new JSONArray());

    public static Model FAILED(String errmsg) {
        return new Model(ErrorCode.FAILED, errmsg);
    }

    public static Model formJson(JSONObject jsonObject) {
        Model responseMessage = new Model();
        responseMessage.errno = jsonObject.getInteger("errno");
        responseMessage.errmsg = jsonObject.getString("errmsg");
        responseMessage.data = jsonObject.get("data");

        return responseMessage;
    }

    private int errno = ErrorCode.FAILED;
    private String errmsg;
    private Object data = "";
    private long time = new Date().getTime();

    private Model() {

    }

    public Model(int errno, String errmsg)
    {
        this.errno = errno;
        this.errmsg = errmsg;
    }

    public Model(Object data)
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

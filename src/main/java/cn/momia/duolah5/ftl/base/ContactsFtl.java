package cn.momia.duolah5.ftl.base;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by ysm on 15-7-20.
 */
public class ContactsFtl implements Ftl {
    private String name;
    private String mobile;

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public ContactsFtl(JSONObject userJson) {
        String name = userJson.getString("name");
        if (StringUtils.isBlank(name)) name = userJson.getString("nickName");

        this.name = name;
        this.mobile = userJson.getString("mobile");
    }
}

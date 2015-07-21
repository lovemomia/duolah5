package cn.momia.duolah5.dto.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * Created by ysm on 15-7-17.
 */
public class UserFtl implements Dto{
    private String avatar;
    private String nickName;
    private String mobile;
    private String sex;
    private String city;
    private Set<Map<String,Object>> children;

    public String getAvatar() {
        return avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getSex() {
        return sex;
    }

    public String getCity() {
        return city;
    }

    public Set<Map<String, Object>> getChildren() {
        return children;
    }

    public UserFtl(JSONObject userJson) {
        JSONObject parseJson = userJson.getJSONObject("user");
        this.avatar = parseJson.getString("avatar");
        this.nickName = parseJson.getString("nickName");
        this.mobile = parseJson.getString("mobile");
        this.sex = parseJson.getString("sex");
        this.city= parseJson.getString("city");
        JSONArray childrenArray = userJson.getJSONArray("children");
        Set<Map<String, Object>> participantFtls = new HashSet<Map<String, Object>>();
        for(int i=0; i<childrenArray.size(); i++) {
            Map<String,Object> child = new HashMap<String, Object>();
            child.put("id", childrenArray.getJSONObject(i).getString("id"));
            child.put("name", childrenArray.getJSONObject(i).getString("name"));
            child.put("sex", childrenArray.getJSONObject(i).getString("sex"));
            child.put("birthday", childrenArray.getJSONObject(i).getDate("birthday"));
            participantFtls.add(child);
        }
        this.children = participantFtls;

    }
}

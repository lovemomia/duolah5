package cn.momia.duolah5.dto.base;

import cn.momia.common.secret.MobileEncryptor;
import cn.momia.common.web.img.ImageFile;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.*;

/**
 * Created by ysm on 15-7-17.
 */
public class UserFtl implements Dto {
    private String token;
    private String name;
    private boolean hasPassword;
    @JSONField(format = "yyyy-MM-dd")
    private Date birthday;
    private String avatar;
    private String nickName;
    private String mobile;
    private String sex;
    private String city;
    private String address;
    private ListDto children;


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

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public boolean isHasPassword() {
        return hasPassword;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getAddress() {
        return address;
    }

    public ListDto getChildren() {
        return children;
    }
    public UserFtl(JSONObject userPackJson) {
        this(userPackJson, false);
    }
    public UserFtl(JSONObject userPackJson, boolean showToken) {
        JSONObject userJson = userPackJson.getJSONObject("user");
        JSONArray childrenJson = userPackJson.getJSONArray("children");

        if (showToken) this.token = userJson.getString("token");
        this.nickName = userJson.getString("nickName");
        this.mobile = MobileEncryptor.encrypt(userJson.getString("mobile"));
        this.hasPassword = userJson.getBoolean("hasPassword");
        this.avatar = ImageFile.url(userJson.getString("avatar"));
        this.name = userJson.getString("name");
        this.sex = userJson.getString("sex");
        this.birthday = userJson.getDate("birthday");
        this.city = userJson.getString("city");
        this.address = userJson.getString("address");

        this.children = new ListDto();
        for (int i = 0; i < childrenJson.size(); i++) {
            JSONObject childJson = childrenJson.getJSONObject(i);
            this.children.add(new ParticipantFtl(childJson, false));
        }
    }
}

package cn.momia.dto.base;

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
        JSONArray childrenJson = userPackJson.getJSONArray("children");

        if (showToken) this.token = userPackJson.getString("token");
        this.nickName = userPackJson.getString("nickName");
        this.mobile = userPackJson.getString("mobile");
        this.hasPassword = userPackJson.getBoolean("hasPassword");
        this.avatar = ImageFile.url(userPackJson.getString("avatar"));
        this.name = userPackJson.getString("name");
        this.sex = userPackJson.getString("sex");
      //  this.birthday = userPackJson.getDate("birthday");
        this.city = userPackJson.getString("city");
        this.address = userPackJson.getString("address");

        this.children = new ListDto();
        for (int i = 0; i < childrenJson.size(); i++) {
            JSONObject childJson = childrenJson.getJSONObject(i);
            this.children.add(new ParticipantFtl(childJson, false));
        }
    }
}

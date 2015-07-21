package cn.momia.duolah5.dto.base;

import cn.momia.common.misc.AgeUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by ysm on 15-7-17.
 */
public class ParticipantFtl implements Dto{
    private long id;
    private String name;
    private String sex;
    @JSONField(format = "yyyy-MM-dd") private Date birthday;
    private String type;
    private Integer idType;
    private String idNo;


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getType() {
        return type;
    }

    public Integer getIdType() {
        return idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public Date getBirthday() {
        return birthday;
    }
    public ParticipantFtl(JSONObject parseJson, boolean showIdNo) {
        this.id = parseJson.getLong("id");
        this.name = parseJson.getString("name");
        this.sex = parseJson.getString("sex");
        this.birthday = parseJson.getDate("birthday");
        this.type = AgeUtil.isAdult(this.birthday) ? "成人" : "儿童";

        if (showIdNo) {
            this.idType = parseJson.getInteger("idType");
            this.idNo = parseJson.getString("idNo");
        }

    }
}

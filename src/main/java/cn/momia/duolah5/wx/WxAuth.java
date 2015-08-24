package cn.momia.duolah5.wx;

import cn.momia.common.utils.config.Configuration;
import cn.momia.duolah5.web.secret.SecretKey;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class WxAuth {
    private static final Logger LOGGER = LoggerFactory.getLogger(WxAuth.class);

    private static Configuration conf;

    private static String accessToken;
    private static String jsapiTicket;

    private static Date lastUpdateTime;

    public void setConf(Configuration conf) {
        WxAuth.conf = conf;
    }

    public void init() {
        update(new Date());
    }

    public static String getJsapiTicket() {
        Date now = new Date();
        if (isExpired(now)) update(now);

        return jsapiTicket;
    }

    private static boolean isExpired(Date now) {
        // TODO 7200 的过期时间根据微信接口返回的数据来定
        return lastUpdateTime == null || lastUpdateTime.before(new Date(now.getTime() - 7200 * 1000));
    }

    private static synchronized void update(Date now) {
        if (!isExpired(now)) return;
        updateAccessToken();
        updateJsapiTicket();

        lastUpdateTime = now;
    }

    private static void updateAccessToken() {
        LOGGER.info("update access token");
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + conf.getString("Weixin.JsApiAppId") + "&secret=" + SecretKey.get("wechatpayJsApiKey"));
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new RuntimeException("fail to execute request: " + request);
            }

            String entity = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONObject responseJson = JSON.parseObject(entity);
            accessToken = responseJson.getString("access_token");
        } catch (Exception e) {
            LOGGER.error("fail to update access token", e);
        }
    }

    private static void updateJsapiTicket() {
        if (StringUtils.isBlank(accessToken)) return;
        LOGGER.info("update jsapi ticket");
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi");
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new RuntimeException("fail to execute request: " + request);
            }

            String entity = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONObject responseJson = JSON.parseObject(entity);
            if (responseJson.getInteger("errcode") == 0) jsapiTicket = responseJson.getString("ticket");
        } catch (Exception e) {
            LOGGER.error("fail to update jsapi ticket", e);
        }
    }
}

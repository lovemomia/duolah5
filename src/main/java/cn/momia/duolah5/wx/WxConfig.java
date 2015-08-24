package cn.momia.duolah5.wx;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.UUID;

public class WxConfig {
    private String appId;
    private String nonceStr;
    private String timeStamp;
    private String sign;

    public String getAppId() {
        return appId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public WxConfig(String appId, String url) {
        this.appId = appId;
        calcSign(WxAuth.getJsapiTicket(), url);
    }

    public void calcSign(String jsapi_ticket, String url) {
        nonceStr = create_nonce_str();
        timeStamp = create_timestamp();
        String signStr = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timeStamp +
                "&url=" + url;

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(signStr.getBytes("UTF-8"));
            sign = byteToHex(crypt.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}

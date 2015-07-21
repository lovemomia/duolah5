package cn.momia.duolah5.common;

import cn.momia.common.web.http.MomiaHttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ysm on 15-7-17.
 */
public class HttpExecute {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpExecute.class);

    public JSONObject getJsonObject(MomiaHttpRequest request) {
        final HttpClient httpClient = HttpClients.createDefault();
        Map<String, Object> user = new HashMap<String, Object>();
        String entity = "";
        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
            entity = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            LOGGER.error("fail to execute request");
            e.printStackTrace();
        }

        return JSON.parseObject(entity);
    }
}

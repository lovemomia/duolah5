package cn.momia.mapi.api;

import cn.momia.common.config.Configuration;
import cn.momia.common.web.http.*;
import cn.momia.common.web.response.ErrorCode;
import cn.momia.common.web.response.ResponseMessage;
import cn.momia.duolah5.dto.base.Dto;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public  class AbstractApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractApi.class);

    @Autowired
    protected Configuration conf;

    @Autowired
    protected MomiaHttpClient httpClient;

    @Autowired
    protected MomiaHttpRequestExecutor requestExecutor;

    protected String baseServiceUrl(Object... paths) {
        return serviceUrl(conf.getString("Service.Base"), paths);
    }

    private String serviceUrl(String service, Object... paths) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(service);
        for (Object path : paths) {
            urlBuilder.append("/").append(path);
        }

        return urlBuilder.toString();
    }

    protected String dealServiceUrl(Object... paths) {
        return serviceUrl(conf.getString("Service.Deal"), paths);
    }

    protected ResponseMessage executeRequest(MomiaHttpRequest request) {
        return executeRequest(request, null);
    }

    protected ResponseMessage executeRequest(MomiaHttpRequest request, Function<Object, Dto> buildResponseData) {
        try {
            JSONObject responseJson = httpClient.execute(request);
            if (buildResponseData == null || responseJson.getInteger("errno") != ErrorCode.SUCCESS) return ResponseMessage.formJson(responseJson);
            return new ResponseMessage(buildResponseData.apply(responseJson.get("data")));
        } catch (Exception e) {
            LOGGER.error("fail to execute request: {}", request, e);
            return new ResponseMessage(ErrorCode.FAILED, "fail to execute request");
        }
    }

    protected MomiaHttpResponseCollector executeRequests(List<MomiaHttpRequest> requests) {
        return  requestExecutor.execute(requests);

    }
    protected long getUserId(String utoken) {
        MomiaHttpParamBuilder builder = new MomiaHttpParamBuilder().add("utoken", utoken);
        MomiaHttpRequest request = MomiaHttpRequest.GET(baseServiceUrl("user"), builder.build());

        ResponseMessage response = executeRequest(request);
        if (response.successful()) return ((JSONObject) response.getData()).getJSONObject("user").getLong("id");
        if (response.getErrno() == ErrorCode.TOKEN_EXPIRED) return 0;

        throw new RuntimeException("fail to get user id");
    }


}

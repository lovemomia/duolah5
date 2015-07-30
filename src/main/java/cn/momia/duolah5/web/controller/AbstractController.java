package cn.momia.duolah5.web.controller;

import cn.momia.common.utils.config.Configuration;
import cn.momia.duolah5.web.http.MomiaHttpRequest;
import cn.momia.duolah5.web.http.MomiaHttpRequestExecutor;
import cn.momia.duolah5.web.http.MomiaHttpResponseCollector;
import cn.momia.duolah5.web.response.ResponseMessage;
import com.google.common.base.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public  class AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);
    @Autowired protected Configuration conf;
    @Autowired protected MomiaHttpRequestExecutor requestExecutor;

    protected String url(Object... paths) {
        // TODO 根据paths判断使用哪个service
        return serviceUrl(conf.getString("Service.Base"), paths);
    }

    private String serviceUrl(String service, Object... paths) {
        StringBuilder urlBuilder = new StringBuilder().append(service);
        for (Object path : paths) urlBuilder.append("/").append(path);

        return urlBuilder.toString();
    }

    protected ResponseMessage executeRequest(MomiaHttpRequest request) {
        return executeRequest(request, null);
    }

    protected ResponseMessage executeRequest(MomiaHttpRequest request, Function<Object, Object> buildResponseData) {
        ResponseMessage responseMessage = requestExecutor.execute(request);

        if (buildResponseData == null || !responseMessage.successful()) return responseMessage;
        return new ResponseMessage(buildResponseData.apply(responseMessage.getData()));
    }

    protected ResponseMessage executeRequests(List<MomiaHttpRequest> requests, Function<MomiaHttpResponseCollector, Object> buildResponseData) {
        MomiaHttpResponseCollector collector = requestExecutor.execute(requests);

        if (collector.notLogin()) return ResponseMessage.TOKEN_EXPIRED;
        if (!collector.isSuccessful()) {
            LOGGER.error("fail to execute requests: {}", collector.getExceptions());
            return ResponseMessage.FAILED;
        }

        return new ResponseMessage(buildResponseData.apply(collector));
    }

}

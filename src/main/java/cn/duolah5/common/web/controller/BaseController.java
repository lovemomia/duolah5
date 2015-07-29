package cn.duolah5.common.web.controller;

import cn.duolah5.common.web.exception.MomiaExpiredException;
import cn.duolah5.common.web.exception.MomiaFailedException;
import cn.duolah5.common.web.response.ErrorCode;
import cn.duolah5.common.web.response.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler
    public ResponseMessage exception(Exception exception) {
        LOGGER.error("exception!!", exception);

        if(exception instanceof MomiaFailedException) {
            return ResponseMessage.FAILED(exception.getMessage());
        } else if (exception instanceof MomiaExpiredException) {
            return ResponseMessage.TOKEN_EXPIRED;
        } else if (exception instanceof MissingServletRequestParameterException) {
            return ResponseMessage.BAD_REQUEST;
        } else {
            return new ResponseMessage(ErrorCode.INTERNAL_SERVER_ERROR, "500 internal server error");
        }
    }
}

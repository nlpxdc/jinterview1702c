package io.cjf.jinterviewback.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ClientForbiddenException extends RuntimeException {

    private String errCode;

    public ClientForbiddenException(String errCode, String errMsg){
        super(errMsg);
        this.errCode = errCode;
    }
}

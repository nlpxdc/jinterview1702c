package io.cjf.jinterviewback.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ClientUnauthorizedException extends RuntimeException {

    private String errCode;

    public ClientUnauthorizedException(String errCode, String errMsg){
        super(errMsg);
        this.errCode = errCode;
    }
}

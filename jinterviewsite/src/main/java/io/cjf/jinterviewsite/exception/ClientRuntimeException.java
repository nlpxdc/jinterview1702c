package io.cjf.jinterviewsite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ClientRuntimeException extends RuntimeException {

    private String errCode;

    public ClientRuntimeException(String errCode, String errMsg){
        super(errMsg);
        this.errCode = errCode;
    }
}

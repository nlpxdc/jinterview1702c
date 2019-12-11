package io.cjf.jinterviewback.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class ClientExceptionDTO {

    @JSONField(name = "path")
    private String path;

    @JSONField(name = "trace")
    private String trace;

    @JSONField(name = "error")
    private String error;

    @JSONField(name = "message")
    private String message;

    @JSONField(name = "timestamp")
    private Date timestamp;

    @JSONField(name = "status")
    private int status;

    public ClientExceptionDTO(int status, String message) {
        this.timestamp = new Date();
        this.status = status;
        this.error = "Client Exception";
        this.message = message;
        this.trace = "";
        this.path = "";
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getTrace() {
        return trace;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
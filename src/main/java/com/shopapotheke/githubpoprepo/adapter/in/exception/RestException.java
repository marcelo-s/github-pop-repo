package com.shopapotheke.githubpoprepo.adapter.in.exception;

public class RestException extends RuntimeException {
    public RestException(String description, Throwable cause) {
        super(description, cause);
    }
}

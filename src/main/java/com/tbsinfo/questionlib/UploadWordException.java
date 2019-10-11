package com.tbsinfo.questionlib;

import org.springframework.stereotype.Component;

/**
 * @author jayMamba
 * @date 2019/10/11
 * @time 15:16
 * @desc
 */
@Component
public class UploadWordException extends RuntimeException {
    public UploadWordException() {

    }
    public UploadWordException(String message) {
        super(message);
    }
}

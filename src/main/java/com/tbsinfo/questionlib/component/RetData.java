package com.tbsinfo.questionlib.component;

import lombok.Data;


/**
 * @author jwz
 * @date 2019/9/25
 * @time 14:33
 * @desc 统一返回对象
 */
@Data
public class RetData<T> {

    private String code="200";

    private String message="提交成功";

    private T body;

    public RetData success(T body) {
        this.body=body;
        return this;
    }

    public RetData erro(String code, String message) {
        this.code=code;
        this.message=message;
        return this;
    }

}

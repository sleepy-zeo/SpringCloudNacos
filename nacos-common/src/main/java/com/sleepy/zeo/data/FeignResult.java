package com.sleepy.zeo.data;

public class FeignResult {

    public int code;
    public String desc;
    public Object data;
    public Throwable throwable;

    public FeignResult(){}

    public FeignResult(int code, String desc, Object data, Throwable throwable) {
        this.code = code;
        this.desc = desc;
        this.data = data;
        this.throwable = throwable;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public String toString() {
        return "FeignResult[" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                ", data=" + data +
                ", throwable=" + throwable +
                ']';
    }
}

package com.example.smartcity1125.bean;

public class loginbean {

    /**
     * msg : 操作成功
     * code : 200
     * token : eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6ImVlZWI4ZWFmLTk1OTEtNDZkZS04NDZjLTkwMjk3NWRkNDNhMSJ9.TRTu9FRd8wZgr0kGiCeIADNuraKcfEDyz32fLGEWBfkC3NFeQcI--uogVrWyBA3homaAjJzmTYjGInRMbnjWAA
     */

    private String msg;
    private int code;
    private String token;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

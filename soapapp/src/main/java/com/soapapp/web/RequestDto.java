package com.soapapp.web;

public class RequestDto {

    private String stringValue;
    private byte[] byteArrayValue;

    // getters y setters
    // ...
    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public byte[] getByteArrayValue() {
        return byteArrayValue;
    }

    public void setByteArrayValue(byte[] byteArrayValue) {
        this.byteArrayValue = byteArrayValue;
    }
}

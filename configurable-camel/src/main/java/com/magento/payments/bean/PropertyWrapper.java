package com.magento.payments.bean;

import org.springframework.beans.factory.annotation.Value;

public class PropertyWrapper {

    @Value("${health.response}")
    private String value;

    public PropertyWrapper() {
    }

    public PropertyWrapper(String value) {
        super();
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

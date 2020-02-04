package com.siit.xml.dtos;

import java.io.Serializable;

public class TokenDTO implements Serializable {
    private String value;

    public TokenDTO() { }

    public TokenDTO(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
package com.api.aggregator.domain.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {
    public Token(String tokenAuth) {
        this.tokenAuth = tokenAuth;
        this.type = "Bearer";
    }

    private String tokenAuth;
    private String type;
}

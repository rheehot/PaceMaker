package com.tatinic.issuetracker.domain.account;

import jdk.nashorn.internal.ir.LiteralNode;
import lombok.Getter;

@Getter
public enum LoginType {

    OAUTH("OAUTH"), BASIC("BASIC"), GUEST("GUEST");

    private String value;

    LoginType(String value) {
        this.value = value;
    }
}

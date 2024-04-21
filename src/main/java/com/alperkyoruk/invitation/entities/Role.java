package com.alperkyoruk.invitation.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_ADMIN("ADMIN"),
    ROLE_MODERATOR("MODERATOR");


    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}

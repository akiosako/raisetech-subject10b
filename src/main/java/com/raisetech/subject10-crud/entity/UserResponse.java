package com.raisetech.restapiexc.entity;

import lombok.Data;

@Data

public class UserResponse {
    private int id;
    private String name;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }
}

package com.raisetech.crudsample.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UpdateForm {
    private int id;
    @NotBlank//null,空文字でないことを検証
    private String name;
}


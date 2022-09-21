package com.raisetech.crudsample.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor

public class InsertForm {
    private int id;
    @NotBlank
    private String name;
}

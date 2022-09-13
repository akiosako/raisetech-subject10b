package com.raisetech.crudsample.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Setter
public class InsertForm {
    private String name;
    private int id;
}

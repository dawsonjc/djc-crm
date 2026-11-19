package com.brewery.web.dto.formdata;

import java.util.Map;

public interface FormData {
    Map<String, String> getErrors();

    default boolean verify() {
        return this.getErrors().isEmpty();
    };
}
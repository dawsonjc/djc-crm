package com.brewery.web.dto.formdata;

import java.util.Map;

public interface FormData {
    Map<String, String> getErrors();

    boolean verify();
}

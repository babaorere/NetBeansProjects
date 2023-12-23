package com.isiweekloan.controller;

import com.isiweekloan.dto.CompanyDto;

import java.util.Collections;
import java.util.List;

public class CompanyBuilder {
    public static List<String> getIds() {
        return Collections.singletonList("1");
    }

    public static CompanyDto getDto() {
        CompanyDto dto = new CompanyDto();
        dto.setId("1");
        return dto;
    }
}

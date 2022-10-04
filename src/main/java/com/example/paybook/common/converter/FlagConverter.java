package com.example.paybook.common.converter;

import javax.persistence.AttributeConverter;

public class FlagConverter implements AttributeConverter<Boolean, String> {


    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return attribute ? "T" : "F";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return dbData.equals("T");
    }
}

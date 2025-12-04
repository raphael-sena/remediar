package com.remediar.back_remediar.exceptions;

import lombok.Getter;

@Getter
public class FieldMessage {
    private final String fieldName;
    private final String message;

    public FieldMessage(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}

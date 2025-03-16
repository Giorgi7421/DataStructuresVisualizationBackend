package org.gpavl.datastructuresvisualizationbackend.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.gpavl.datastructuresvisualizationbackend.model.arrayvector.ArrayVectorCreateRequest;
import org.gpavl.datastructuresvisualizationbackend.validation.annotation.ValidArrayVectorCreateRequest;

public class ArrayVectorCreateRequestValidator implements ConstraintValidator<ValidArrayVectorCreateRequest, ArrayVectorCreateRequest<?>> {

    @Override
    public boolean isValid(ArrayVectorCreateRequest<?> arrayVectorCreateRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (arrayVectorCreateRequest.getName() == null || arrayVectorCreateRequest.getName().isBlank() || arrayVectorCreateRequest.getDataType() == null) {
            return false;
        }

        if (!isValidConstructor(arrayVectorCreateRequest)) {
            return false;
        }

        if (isDefaultConstructor(arrayVectorCreateRequest)) {
            return true;
        }

        return switch (arrayVectorCreateRequest.getDataType()) {
            case INT -> arrayVectorCreateRequest.getValue().getClass().equals(Integer.class);
            case BOOL -> arrayVectorCreateRequest.getValue().getClass().equals(Boolean.class);
            case CHAR -> arrayVectorCreateRequest.getValue().getClass().equals(Character.class);
            case FLOAT -> arrayVectorCreateRequest.getValue().getClass().equals(Double.class);
            case STRING -> arrayVectorCreateRequest.getValue().getClass().equals(String.class);
        };
    }

    private boolean isDefaultConstructor(ArrayVectorCreateRequest<?> arrayVectorCreateRequest) {
        return arrayVectorCreateRequest.getAmount() == null && arrayVectorCreateRequest.getValue() == null;
    }

    private boolean isValidConstructor(ArrayVectorCreateRequest<?> arrayVectorCreateRequest) {
        return (arrayVectorCreateRequest.getAmount() == null) == (arrayVectorCreateRequest.getValue() == null);
    }
}

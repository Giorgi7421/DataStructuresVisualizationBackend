package org.gpavl.datastructuresvisualizationbackend.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.gpavl.datastructuresvisualizationbackend.model.vector.arrayvector.ArrayVectorCreateRequest;
import org.gpavl.datastructuresvisualizationbackend.validation.annotation.ValidArrayVectorCreateRequest;

public class ArrayVectorCreateRequestValidator implements ConstraintValidator<ValidArrayVectorCreateRequest, ArrayVectorCreateRequest> {

    @Override
    public boolean isValid(ArrayVectorCreateRequest arrayVectorCreateRequest, ConstraintValidatorContext constraintValidatorContext) {
        return isValidConstructor(arrayVectorCreateRequest);
    }

    private boolean isValidConstructor(ArrayVectorCreateRequest arrayVectorCreateRequest) {
        return (arrayVectorCreateRequest.getAmount() == null) == (arrayVectorCreateRequest.getValue() == null);
    }
}

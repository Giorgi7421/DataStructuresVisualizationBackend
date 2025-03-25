package org.gpavl.datastructuresvisualizationbackend.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.gpavl.datastructuresvisualizationbackend.model.vector.VectorCreateRequest;
import org.gpavl.datastructuresvisualizationbackend.validation.annotation.ValidVectorCreateRequest;

public class VectorCreateRequestValidator implements ConstraintValidator<ValidVectorCreateRequest, VectorCreateRequest> {

    @Override
    public boolean isValid(VectorCreateRequest vectorCreateRequest, ConstraintValidatorContext constraintValidatorContext) {
        return isValidConstructor(vectorCreateRequest);
    }

    private boolean isValidConstructor(VectorCreateRequest vectorCreateRequest) {
        return (vectorCreateRequest.getAmount() == null) == (vectorCreateRequest.getValue() == null);
    }
}

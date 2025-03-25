package org.gpavl.datastructuresvisualizationbackend.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.gpavl.datastructuresvisualizationbackend.validation.impl.VectorCreateRequestValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = VectorCreateRequestValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidVectorCreateRequest {
    String message() default "Invalid vector creation request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

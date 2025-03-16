package org.gpavl.datastructuresvisualizationbackend.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.gpavl.datastructuresvisualizationbackend.validation.impl.ArrayVectorCreateRequestValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ArrayVectorCreateRequestValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidArrayVectorCreateRequest {
    String message() default "Invalid array vector creation request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

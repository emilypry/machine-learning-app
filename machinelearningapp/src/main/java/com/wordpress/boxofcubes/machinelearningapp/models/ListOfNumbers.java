package com.wordpress.boxofcubes.machinelearningapp.models;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ListOfNumbersValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ListOfNumbers {
    String message() default "The data is not a legible set of numbers";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
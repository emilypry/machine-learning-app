package com.wordpress.boxofcubes.machinelearningapp.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wordpress.boxofcubes.machinelearningapp.validation.ListOfNumbersValidator;

@Documented
@Constraint(validatedBy = ListOfNumbersValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ListOfNumbers {
    String message() default "The data is not a legible set of numbers";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
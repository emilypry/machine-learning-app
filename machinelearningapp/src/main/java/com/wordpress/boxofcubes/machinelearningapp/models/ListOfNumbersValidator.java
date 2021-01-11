package com.wordpress.boxofcubes.machinelearningapp.models;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ListOfNumbersValidator implements 
  ConstraintValidator<ListOfNumbers, String> {

    @Override
    public void initialize(ListOfNumbers ListOfNumbers) {
    }

    @Override
    public boolean isValid(String contactField,
      ConstraintValidatorContext cxt) {
        return contactField != null && contactField.matches("[0-9]+")
          && (contactField.length() > 8) && (contactField.length() < 14);
    }

}
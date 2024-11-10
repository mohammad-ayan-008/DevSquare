package org.mainfest.devSquare.DevSqaure.Validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.mainfest.devSquare.DevSqaure.services.BloomFilterService;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String> {

    @Autowired
    private BloomFilterService bloomFilterService;

    @Override
    public void initialize(UniqueUserName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!bloomFilterService.ifSUserNameIsAvailable(s)){
            return true;
        }
        return false;
    }
}

package com.bantads.account.lib;

import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;

public class ValidationViolations {
    public ArrayList<String> violations = new ArrayList<>();

    public ValidationViolations(Set<ConstraintViolation<?>> constraintViolations) {
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            this.violations.add(constraintViolation.getMessage());
        }
    }

}

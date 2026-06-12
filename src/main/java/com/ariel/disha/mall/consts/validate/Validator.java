package com.ariel.disha.mall.consts.validate;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.StringJoiner;

/**
 * @author ariel
 * @apiNote Validator
 * @serial
 */
public class Validator {

    private static final javax.validation.Validator validator;

    static {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    public static void validate(Object obj, Class<?>... gClasses) {
        Set<ConstraintViolation<Object>> set = validator.validate(obj, gClasses);
        if (!set.isEmpty()) {
            StringJoiner joiner = new StringJoiner(";");
            set.forEach(c -> joiner.add(c.getPropertyPath() + c.getMessage()));
            throw new IllegalArgumentException(joiner.toString());
        }
    }

}

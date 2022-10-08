package ru.nikitin.userservice.codemark.utill.validation;


import javax.validation.*;
import javax.validation.groups.Default;
import java.util.Set;

public class ValidationUtil {

    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private ValidationUtil() {
    }

    private static <T> void validate(T bean, Class<?>... groups) {
        Set<ConstraintViolation<T>> violations = validator.validate(bean, groups);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public static <T> String getErrors(T bean) {
        String str = "";
        try {
            validate(bean, Default.class);
        } catch (ConstraintViolationException ex) {
            str = ex.getMessage();
        }
        return str;
    }


}
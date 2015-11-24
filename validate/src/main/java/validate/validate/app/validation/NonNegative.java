package validate.validate.app.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = {NonNegativeConstraintValidator.class})
@Target({ FIELD })
@Retention(RUNTIME)
public @interface NonNegative {
    String message() default "{validate.validate.app.validation.NonNegative.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ FIELD, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        NonNegative[] value();
    }
}
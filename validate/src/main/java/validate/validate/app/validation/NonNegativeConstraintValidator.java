package validate.validate.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NonNegativeConstraintValidator
        implements ConstraintValidator<NonNegative, Integer> {

	@Override
	public void initialize(NonNegative constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		return (value >= 0);
	}
}

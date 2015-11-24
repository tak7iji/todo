package validate.validate.app.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import validate.validate.app.welcome.ValidateForm;

@Component
public class CustomValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ValidateForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(errors.hasErrors()) {
			return;
		}
		
		ValidateForm form = (ValidateForm) target;
		
		int high = form.getHigh();
		int middle = form.getMiddle();
		int low = form.getLow();
		
		if(high + middle + low > form.getThreshold()) {
			Object[] args = {form.getThreshold()};
			errors.reject("validate.validate.app.validation.CustomValidator.message", args, "");
		}

	}

}

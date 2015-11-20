package validate.validate.app.welcome;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import validate.validate.app.validation.CustomValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Inject
	CustomValidator customValidator;

    private static final Logger logger = LoggerFactory
            .getLogger(HomeController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder) {
    	binder.addValidators(customValidator);
    }
    
    @ModelAttribute
    public ValidateForm setUpValidateForm() {
    	return new ValidateForm();
    }
    
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String home(Locale locale, Model model) {
        logger.info("Welcome home! The client locale is {}.", locale);

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
                DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate);

        return "welcome/home";
    }
    
    @RequestMapping(value = "validate", method = RequestMethod.POST)
    public String validate(@Validated ValidateForm form, BindingResult result) {
    	if(result.hasErrors()) {
    		return "welcome/home";
    	}
    	
    	return "validate/success";
    }

}

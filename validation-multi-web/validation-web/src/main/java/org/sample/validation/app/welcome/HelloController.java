package org.sample.validation.app.welcome;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.sample.validation.common.annotation.Audit;
import org.sample.validation.common.annotation.AuditParam;
import org.sample.validation.common.annotation.NonAuditParam;
import org.sample.validation.domain.service.calc.CalcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HelloController {

    private static final Logger logger = LoggerFactory
            .getLogger(HelloController.class);
    
    @Inject
    CalcService calcService;

    /**
     * Simply selects the home view to render by returning its name.
     */
    @Audit
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String home(@AuditParam Locale locale, @NonAuditParam Model model) {
        logger.info("Welcome home! The client locale is {}.", locale);

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
                DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate);

        calcService.sum(10, 15);
        calcService.nop();
        calcService.nullArg(null);
        try {
        	calcService.throwBusinessException();
        } catch (Throwable t) {
        }
//        throw new RuntimeException("foo");
        calcService.throwRuntimeException();
        
        return "welcome/home";
    }

}

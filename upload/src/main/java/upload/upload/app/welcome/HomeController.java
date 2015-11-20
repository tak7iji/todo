package upload.upload.app.welcome;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.message.ResultMessages;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("article")
public class HomeController {

    private static final Logger logger = LoggerFactory
            .getLogger(HomeController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
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

    @Value("${upload.allowableFileSize:1000000}")
    private int uploadAllowableFileSize;

    // omitted

    // (1)
    @ModelAttribute
    public FileUploadForm setFileUploadForm() {
        return new FileUploadForm();
    }

    // (2)
    @RequestMapping(value = "upload", method = RequestMethod.GET, params = "form")
    public String uploadForm() {
        return "article/uploadForm";
    }

    // (3)
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public String upload(@Validated FileUploadForm form,
            BindingResult result, RedirectAttributes redirectAttributes) throws Exception {

        if (result.hasErrors()) {
            return "article/uploadForm";
        }

        MultipartFile uploadFile = form.getFile();

        // (4)
        if (!StringUtils.hasLength(uploadFile.getOriginalFilename())) {
            result.rejectValue(uploadFile.getName(), "e.xx.at.6002");
            return "article/uploadForm";
        }

        // (5)
        if (uploadFile.isEmpty()) {
            result.rejectValue(uploadFile.getName(), "e.xx.at.6003");
            return "article/uploadForm";
        }

        // (6)
        if (uploadAllowableFileSize < uploadFile.getSize()) {
            result.rejectValue(uploadFile.getName(), "e.xx.at.6004",
                    new Object[] { uploadAllowableFileSize }, null);
            return "article/uploadForm";
        }

        // (7)
        logger.info("File name = {}", form.getFile().getName());
        File dest = new File("c:/tmp/file");
        form.getFile().transferTo(dest);

        // (8)
        redirectAttributes.addFlashAttribute(ResultMessages.success().add(
                "i.xx.at.0001"));

        // (9)
        return "redirect:/article/upload?complete";
    }

    @RequestMapping(value = "upload", method = RequestMethod.GET, params = "complete")
    public String uploadComplete() {
        return "article/uploadComplete";
    }
}

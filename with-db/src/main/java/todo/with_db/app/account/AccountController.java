package todo.with_db.app.account;

import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import todo.with_db.domain.model.Account;
import todo.with_db.domain.service.userdetails.SampleUserDetails;

@Controller
@RequestMapping("account")
public class AccountController {

    @RequestMapping
    public String view(
            @AuthenticationPrincipal SampleUserDetails userDetails,
            Model model) {
        Account account = userDetails.getAccount();
        model.addAttribute(account);
        return "account/view";
    }
}
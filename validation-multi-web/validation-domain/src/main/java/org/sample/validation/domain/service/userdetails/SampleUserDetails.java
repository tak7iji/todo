package org.sample.validation.domain.service.userdetails;

import org.sample.validation.domain.model.Account;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SampleUserDetails extends User {
    private static final long serialVersionUID = 1L;

    private final Account account;

    public SampleUserDetails(Account account) {
        super(account.getUsername(), account.getPassword(), AuthorityUtils
                .createAuthorityList("ROLE_USER"));
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

}

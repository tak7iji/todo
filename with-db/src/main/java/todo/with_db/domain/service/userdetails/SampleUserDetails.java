package todo.with_db.domain.service.userdetails;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import todo.with_db.domain.model.Account;

public class SampleUserDetails extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private final Account account;

    public SampleUserDetails(Account account) {
        super(account.getUsername(), account.getPassword(), AuthorityUtils
                .createAuthorityList(account.getAuthorities()));
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}

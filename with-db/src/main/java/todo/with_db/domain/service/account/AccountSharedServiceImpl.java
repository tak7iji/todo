package todo.with_db.domain.service.account;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

import todo.with_db.domain.model.Account;
import todo.with_db.domain.repository.account.AccountRepository;

@Service
public class AccountSharedServiceImpl implements AccountSharedService {
    @Inject
    AccountRepository accountRepository;

    @Transactional(readOnly=true)
    @Override
    public Account findOne(String username) {
        Account account = accountRepository.findOne(username);
        if (account == null) {
            throw new ResourceNotFoundException("The given account is not found! username="
                    + username);
        }
        return account;
    }

}
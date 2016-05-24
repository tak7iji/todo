package todo.domain.service.account;

import todo.domain.model.Account;

public interface AccountSharedService {
    Account findOne(String username);
}
package todo.with_db.domain.service.account;

import todo.with_db.domain.model.Account;

public interface AccountSharedService {
    Account findOne(String username);
}
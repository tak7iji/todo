package todo.with_db.domain.repository.account;

import todo.with_db.domain.model.account.Account;

public interface AccountRepository {
    Account findOne(String username);
}

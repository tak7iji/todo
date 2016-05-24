package todo.domain.repository.account;

import todo.domain.model.Account;

public interface AccountRepository {
    Account findOne(String username);
}
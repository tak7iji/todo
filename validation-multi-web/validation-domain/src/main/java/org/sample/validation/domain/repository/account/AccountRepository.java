package org.sample.validation.domain.repository.account;

import org.sample.validation.domain.model.Account;

public interface AccountRepository {
    Account findOne(String username);
}
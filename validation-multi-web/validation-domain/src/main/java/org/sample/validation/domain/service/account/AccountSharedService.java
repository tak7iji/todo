package org.sample.validation.domain.service.account;

import org.sample.validation.domain.model.Account;

public interface AccountSharedService {
    Account findOne(String username);
}

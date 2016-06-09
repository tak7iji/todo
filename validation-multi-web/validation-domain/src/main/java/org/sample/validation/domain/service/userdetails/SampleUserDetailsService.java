package org.sample.validation.domain.service.userdetails;

import javax.inject.Inject;

import org.sample.validation.domain.model.Account;
import org.sample.validation.domain.service.account.AccountSharedService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

@Service
public class SampleUserDetailsService implements UserDetailsService {
    @Inject
    AccountSharedService accountSharedService;

    @Transactional(readOnly=true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Account account = accountSharedService.findOne(username);
            return new SampleUserDetails(account);
        } catch (ResourceNotFoundException e) {
            throw new UsernameNotFoundException("user not found", e);
        }
    }

}
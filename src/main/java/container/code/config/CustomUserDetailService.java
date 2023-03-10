package container.code.config;

import container.code.data.dto.UserPrinciple;
import container.code.data.entity.Account;
import container.code.data.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    @Cacheable(value = "userDetails")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        return UserPrinciple.build(account);
    }
}

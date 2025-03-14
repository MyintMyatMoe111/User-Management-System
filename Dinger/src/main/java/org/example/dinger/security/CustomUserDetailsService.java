package org.example.dinger.security;

import lombok.RequiredArgsConstructor;
import org.example.dinger.dao.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDao userDao;

    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
        return userDao.findUsername(userNameOrEmail)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(userNameOrEmail));
    }
}

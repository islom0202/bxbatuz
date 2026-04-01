package org.example.bxbatuz.antifraud.security;

import lombok.RequiredArgsConstructor;
import org.example.bxbatuz.antifraud.entity.AdminDetails;
import org.example.bxbatuz.antifraud.repo.AdminDetailsRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AdminDetailsRepo authRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final AdminDetails auth = authRepo.findByUsername(username);
        return new UsersDetails(auth);
    }
}

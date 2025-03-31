package org.example.bxbatuz.Security;

import lombok.RequiredArgsConstructor;
import org.example.bxbatuz.Entity.Auth;
import org.example.bxbatuz.Repo.AuthRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthRepo authRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Auth auth = authRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UsersDetails(auth);
    }
}

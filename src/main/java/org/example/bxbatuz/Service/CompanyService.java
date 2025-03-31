package org.example.bxbatuz.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.bxbatuz.Dto.SignupRequest;
import org.example.bxbatuz.Entity.Auth;
import org.example.bxbatuz.Entity.Company;
import org.example.bxbatuz.Enum.AuthRole;
import org.example.bxbatuz.Repo.AuthRepo;
import org.example.bxbatuz.Repo.CompanyRepo;
import org.example.bxbatuz.Response;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyService {
    private final CompanyRepo companyRepo;
    private final AuthRepo authRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Response save(SignupRequest request) {
        Auth auth = authRepo.save(new Auth(
                request.email(),
                passwordEncoder.encode(request.password()),
                AuthRole.COMPANY));

        Company company = new Company();
        company.setId(auth.getId());
        company.setAuth(auth);
        company.setName(request.companyName());
        company.setEmpCount(request.empCount());
        companyRepo.save(company);
        return new Response("company saved", true);
    }
}

package org.example.bxbatuz.Controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.bxbatuz.Dto.SigninRequest;
import org.example.bxbatuz.Dto.SignupRequest;
import org.example.bxbatuz.Response;
import org.example.bxbatuz.Security.JwtUtil;
import org.example.bxbatuz.Service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final CompanyService companyService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup/company")
    public ResponseEntity<Response> signup(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.ok(companyService.save(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@Valid @RequestBody SigninRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new Token(accessToken));
    }

    @Data
    public static class Token {
        private String accessToken;

        public Token(String accessToken) {
            this.accessToken = accessToken;
        }
    }
}

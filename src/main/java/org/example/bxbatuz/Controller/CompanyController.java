package org.example.bxbatuz.Controller;

import lombok.AllArgsConstructor;
import org.example.bxbatuz.Dto.AddEmployee;
import org.example.bxbatuz.Response;
import org.example.bxbatuz.Service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/add/emp")
    public ResponseEntity<Response> addEmployee(@RequestBody AddEmployee request){
        return ResponseEntity.ok(companyService.addEmployee(request));
    }
}

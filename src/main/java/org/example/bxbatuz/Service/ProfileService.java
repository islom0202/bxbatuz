package org.example.bxbatuz.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bxbatuz.Dto.CompanyProfileResponse;
import org.example.bxbatuz.Dto.EmployeeProfileResponse;
import org.example.bxbatuz.Dto.ProfileResponse;
import org.example.bxbatuz.Entity.Auth;
import org.example.bxbatuz.Entity.Company;
import org.example.bxbatuz.Entity.Employees;
import org.example.bxbatuz.Entity.Files;
import org.example.bxbatuz.Repo.AuthRepo;
import org.example.bxbatuz.Repo.CompanyRepo;
import org.example.bxbatuz.Repo.EmployeesRepo;
import org.example.bxbatuz.Repo.FilesRepo;
import org.example.bxbatuz.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final AuthRepo authRepo;
    private final EmployeesRepo employeesRepo;
    private final CompanyRepo companyRepo;
    private final FilesRepo filesRepo;

    public EmployeeProfileResponse getEmployeeProfile(Long userId) {
        Auth auth = authRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("employee not found"));

        Employees employee = employeesRepo.findByAuth(auth)
                .orElseThrow(() -> new NoSuchElementException("employee not found"));

        return new EmployeeProfileResponse(
                employee.getId(),
                employee.getFullName(),
                auth.getEmail(),
                employee.getPhone(),
                employee.getPosition(),
                employee.getOfficialWorkHours(),
                employee.getStartTime(),
                employee.getEndTime(),
                employee.getCompany().getName(),
                employee.getDepartment().getName()
        );
    }

    public CompanyProfileResponse getCompanyProfile(Long companyId) {
        Auth auth = authRepo.findById(companyId)
                .orElseThrow(() -> new NoSuchElementException("company not found"));

        Company company = companyRepo.findByAuth(auth)
                .orElseThrow(() -> new NoSuchElementException("company not found"));

        return new CompanyProfileResponse(
                company.getId(),
                company.getName(),
                company.getEmpCount(),
                auth.getEmail(),
                company.getAddress()
        );
    }

    public byte[] getEmployeeImageByUserId(Long userId) {
        Employees employee = employeesRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("employee not found"));
        return employee.getImage().getData();
    }

    public byte[] getCompanyImage(Long companyId) {
        Company company = companyRepo.findById(companyId)
                .orElseThrow(() -> new NoSuchElementException("company not found"));
        return company.getImage().getData();
    }

    @Transactional
    public Response uploadImage(Long id, MultipartFile image) throws IOException {
        Auth auth = authRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Files uploadedFile = saveFile(image);

        switch (auth.getRole()) {
            case USER -> uploadEmployeeImage(auth.getId(), uploadedFile);
            case COMPANY -> uploadCompanyImage(auth.getId(), uploadedFile);
            default -> throw new IllegalArgumentException("Unknown role: " + auth.getRole());
        }

        return new Response("Image uploaded successfully", true);
    }

    private Files saveFile(MultipartFile image) throws IOException {
        return filesRepo.save(new Files(
                image.getOriginalFilename(),
                image.getContentType(),
                image.getBytes(),
                LocalDateTime.now()
        ));
    }

    public void uploadCompanyImage(Long id, Files file) {
        Company company = companyRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Company not found"));

        company.setImage(file);
        companyRepo.save(company);
    }

    public void uploadEmployeeImage(Long id, Files file) {
        Employees employee = employeesRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));

        employee.setImage(file);
        employeesRepo.save(employee);
    }

    public byte[] getImage(Long id) {
        Auth auth = authRepo.findById(id)
                .orElseThrow(()->new NoSuchElementException("user not found"));

        switch (auth.getRole()){
            case USER -> {
                return getEmployeeImageByUserId(id);
            }
            case COMPANY -> {
                return getCompanyImage(id);
            }
            default -> throw new IllegalArgumentException("Unknown role: " + auth.getRole());
        }
    }

    public ProfileResponse getProfile(Long id) {
        Auth auth = authRepo.findById(id)
                .orElseThrow(()->new NoSuchElementException("user not found"));

        switch (auth.getRole()){
            case USER -> {
                return getEmployeeProfile(id);
            }
            case COMPANY -> {
                return getCompanyProfile(id);
            }
            default -> throw new IllegalArgumentException("Unknown role: " + auth.getRole());
        }
    }
}

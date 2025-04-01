package org.example.bxbatuz.Service;

import lombok.AllArgsConstructor;
import org.example.bxbatuz.Entity.Department;
import org.example.bxbatuz.Repo.DepartmentRepo;
import org.example.bxbatuz.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService {

    private final DepartmentRepo departmentRepo;
    public Response add(String name) {
        departmentRepo.save(new Department(name));
        return new Response("saved", true);
    }

    public List<Department> getAll() {
        return departmentRepo.findAll();
    }
}

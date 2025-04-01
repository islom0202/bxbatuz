package org.example.bxbatuz.Controller;

import lombok.AllArgsConstructor;
import org.example.bxbatuz.Entity.Department;
import org.example.bxbatuz.Response;
import org.example.bxbatuz.Service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    @PostMapping("/add")
    public ResponseEntity<Response> addDepartment(@RequestParam String name){
        return ResponseEntity.ok(departmentService.add(name));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Department>> getList(){
        return ResponseEntity.ok(departmentService.getAll());
    }
}

package org.example.bxbatuz.antifraud.controller;

import lombok.RequiredArgsConstructor;
import org.example.bxbatuz.antifraud.repo.LinkRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.example.bxbatuz.antifraud.contraints.UriEnum.BASE_URI;

@Validated
@Controller
@RequestMapping("/form")
@RequiredArgsConstructor
public class FormController {
    private  final LinkRepo linkRepo;

    @GetMapping("/{adminId:.+}")
    public String showForm(@PathVariable String adminId, Model model) {
        model.addAttribute("adminId", adminId);

        if (linkRepo.findByGeneratedLink(BASE_URI.getVal().concat(adminId)).getIsExpired())
            return "expired.html";

        return "form.html"; // This refers to registration-form.html in /resources/templates
    }

    @GetMapping("/admin/login")
    public String showLoginForm() {
        return "login.html";
    }

    @GetMapping("/admin/dashboard")
    public String showDashboardForm() {
        return "dashboard.html";
    }

    @GetMapping("/admin/users")
    public String showUsersForm() {
        return "users.html";
    }

    @GetMapping("/admin/links")
    public String showLinksForm() {
        return "link.html";
    }

    @GetMapping("/admin/concurs")
    public String showLinksConcurs() {
        return "concurs.html";
    }
}

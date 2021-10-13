package com.hkimbrough22.codeFellowship.controllers;

import com.hkimbrough22.codeFellowship.models.ApplicationUser;
import com.hkimbrough22.codeFellowship.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @GetMapping("/")
    public String getLogin(Principal p, Model m){
        if(p != null) {
            String username = p.getName();
            ApplicationUser currentUser = applicationUserRepository.findByUsername(username);

            m.addAttribute("username", username);
        }
        return "index.html";
    }
}
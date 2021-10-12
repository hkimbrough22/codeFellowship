package com.hkimbrough22.codeFellowship.controllers;

import com.hkimbrough22.codeFellowship.models.ApplicationUser;
import com.hkimbrough22.codeFellowship.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ApplicationUserController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String getLogin(){
        return "login.html";
    }

    @GetMapping("/signup")
    public String getSignUp(){
        return "signup.html";
    }

    @PostMapping("/signup")
    public RedirectView createNewUser(String username, String password){
        ApplicationUser newUser = new ApplicationUser();
        newUser.setUsername(username);
        String hashedPassword = passwordEncoder.encode(password);
        newUser.setPassword(hashedPassword);

        applicationUserRepository.save(newUser);
        return new RedirectView("/");
    }
}

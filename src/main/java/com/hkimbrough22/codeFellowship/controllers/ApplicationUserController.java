package com.hkimbrough22.codeFellowship.controllers;

import com.hkimbrough22.codeFellowship.models.ApplicationUser;
import com.hkimbrough22.codeFellowship.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.file.Path;
import java.security.Principal;

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

    @GetMapping("/myprofile")
    public String getUserInfo(Principal p, Model m){
        if(p != null) {
            String username = p.getName();
            ApplicationUser currentUser = applicationUserRepository.findByUsername(username);

            m.addAttribute("user", currentUser);
        }
        return "myprofile.html";
    }

    @GetMapping("user-info/{id}")
    public String getOtherUser(@PathVariable Long id, Model m, Principal p){
        if (p != null)
        {
            String username = p.getName();
            ApplicationUser currentUser = applicationUserRepository.findByUsername(username);
            m.addAttribute("user", currentUser);
        }
        ApplicationUser userToSee = applicationUserRepository.findById(id).orElseThrow();
        m.addAttribute("userToSee", userToSee);

        // Maybe implement? m.addAttribute("testDate", LocalDateTime.now());

        return "/user-info.html";
    }

    @PutMapping("/myprofile")
    public RedirectView editUserPage(Model m, Principal p, String username, String firstName, String lastName, String bio, String birthday){
        if(p != null){
            ApplicationUser currentUser = applicationUserRepository.findByUsername(p.getName());
            currentUser.setFirstName(firstName);
            currentUser.setLastName(lastName);
            currentUser.setBio(bio);
            currentUser.setBirthday(birthday);

            applicationUserRepository.save(currentUser);
            m.addAttribute("user", currentUser);
        }
        return new RedirectView("/myprofile");
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
        return new RedirectView("/"); //switch to userInfo page
    }
}

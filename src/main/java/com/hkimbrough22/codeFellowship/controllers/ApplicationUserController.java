package com.hkimbrough22.codeFellowship.controllers;

import com.hkimbrough22.codeFellowship.models.ApplicationUser;
import com.hkimbrough22.codeFellowship.models.Post;
import com.hkimbrough22.codeFellowship.repositories.ApplicationUserRepository;
import com.hkimbrough22.codeFellowship.repositories.PostRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class ApplicationUserController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String getLogin() {
        return "login.html";
    }

    @GetMapping("user-info/{id}")
    public String getOtherUser(@PathVariable Long id, Model m, Principal p) {
        if (p != null) {
            String username = p.getName();
            ApplicationUser currentUser = applicationUserRepository.findByUsername(username);

            ApplicationUser userToSee = applicationUserRepository.findById(id).orElseThrow();
            Set<ApplicationUser> friends = userToSee.getInvitees();

            List<Post> posts = userToSee.getMyPosts();
            List<Post> friendsPosts = new ArrayList<>();
            for(ApplicationUser friend : currentUser.getInvitors()) {
                List<Post> friendPosts = friend.getMyPosts();
                friendsPosts.addAll(friendPosts);
            }

            m.addAttribute("user", currentUser);
            m.addAttribute("userToSee", userToSee);
            m.addAttribute("userToSeeID", id);
            m.addAttribute("friends", friends);
            m.addAttribute("posts", posts);
            m.addAttribute("friendsPosts", friendsPosts);
        }

        // Maybe implement? m.addAttribute("testDate", LocalDateTime.now());

        return "/user-info.html";
    }

    @PutMapping("/add-friend/{userToAddID}")
    public RedirectView addFriend(@PathVariable Long userToAddID, Principal p) {
        if (p == null) {
            throw new IllegalArgumentException("You must be logged in to add friends!");
        }

        ApplicationUser currentUser = applicationUserRepository.findByUsername(p.getName());
        ApplicationUser userToAdd = applicationUserRepository.findById(userToAddID).orElseThrow();

        currentUser.getInvitees().add(userToAdd);
        applicationUserRepository.save(currentUser);

        return new RedirectView("/user-info/" + currentUser.getId());
    }

    @GetMapping("/myprofile")
    public String getUserInfo(Principal p, Model m) {
        if (p != null) {
            String username = p.getName();
            ApplicationUser currentUser = applicationUserRepository.findByUsername(username);
            Set<ApplicationUser> friends = currentUser.getInvitees();

            List<Post> friendsPosts = new ArrayList<>();
            for(ApplicationUser friend : friends) {
                List<Post> friendPosts = friend.getMyPosts();
                friendsPosts.addAll(friendPosts);
            }

            m.addAttribute("friends", friends);
            m.addAttribute("posts", currentUser.getMyPosts());
            m.addAttribute("friendsPosts", friendsPosts);
            m.addAttribute("user", currentUser);
        }
        return "myprofile.html";
    }

    @PutMapping("/myprofile")
    public RedirectView editUserPage(Model m, Principal p, String username, String firstName, String lastName, String bio, String birthday) {
        if (p != null) {
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
    public String getSignUp() {
        return "signup.html";
    }

    @PostMapping("/signup")
    public RedirectView createNewUser(String username, String password) {
        ApplicationUser newUser = new ApplicationUser();
        newUser.setUsername(username);
        String hashedPassword = passwordEncoder.encode(password);
        newUser.setPassword(hashedPassword);

        applicationUserRepository.save(newUser);
        return new RedirectView("/"); //switch to userInfo page
    }

    @PostMapping("/add-post")
    public RedirectView addAPost(String body, String username){
        ApplicationUser userPosting = applicationUserRepository.findByUsername(username);
        Post newPost = new Post(body, userPosting);

        postRepository.save(newPost);
//        userPosting.getMyPosts().add(newPost);
        applicationUserRepository.save(userPosting);

        return new RedirectView("/myprofile");
    }
}

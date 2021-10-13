package com.hkimbrough22.codeFellowship.models;

import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String body;
    LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    ApplicationUser myUser;

    protected Post(){};

    public Post(String body, ApplicationUser myUser) {
        this.body = body;
        this.myUser = myUser;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ApplicationUser getMyUser() {
        return myUser;
    }

    public void setMyUser(ApplicationUser myUser) {
        this.myUser = myUser;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

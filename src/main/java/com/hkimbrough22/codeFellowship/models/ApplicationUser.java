package com.hkimbrough22.codeFellowship.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class ApplicationUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String birthday;
    private String bio;

    public Long getId() {
        return id;
    }

    public List<Post> getMyPosts() {
        return myPosts;
    }

    @OneToMany(mappedBy = "myUser")
    List<Post> myPosts = new ArrayList<>();

    @ManyToMany(mappedBy = "invitees")
    Set<ApplicationUser> invitors;

    @ManyToMany
    @JoinTable(
        name="friendInvitor_to_friendInvitee",
            joinColumns = {@JoinColumn(name = "invitor")},
            inverseJoinColumns = {@JoinColumn(name = "invitee")}
    )
    Set<ApplicationUser> invitees;

    public Set<ApplicationUser> getInvitors() {
        return invitors;
    }

    public Set<ApplicationUser> getInvitees() {
        return invitees;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return null;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}

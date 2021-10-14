package com.hkimbrough22.codeFellowship.repositories;

import com.hkimbrough22.codeFellowship.models.ApplicationUser;
import com.hkimbrough22.codeFellowship.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByMyUser(ApplicationUser myUser);
}

package com.hkimbrough22.codeFellowship.repositories;

import com.hkimbrough22.codeFellowship.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
}

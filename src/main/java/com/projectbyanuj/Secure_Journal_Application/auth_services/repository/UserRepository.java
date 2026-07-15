package com.projectbyanuj.Secure_Journal_Application.auth_services.repository;

import com.projectbyanuj.Secure_Journal_Application.auth_services.entity.AppUser;
import com.projectbyanuj.Secure_Journal_Application.auth_services.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<AppUser,String> {
    Optional<AppUser> findUserByEmail(String email);
    boolean existsAppUserByEmail(String email);
    boolean existsByRole(Role role);
}

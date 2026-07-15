package com.projectbyanuj.Secure_Journal_Application.journal_apis.repository;

import com.projectbyanuj.Secure_Journal_Application.auth_services.entity.AppUser;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.JournalResponse;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {
    List<Journal> findAllByOwnerUserId(Long userId);
    Optional<Journal> findByIdAndOwnerUserId(Long id, Long userId);
}

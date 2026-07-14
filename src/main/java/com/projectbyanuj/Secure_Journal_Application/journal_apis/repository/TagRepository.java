package com.projectbyanuj.Secure_Journal_Application.journal_apis.repository;

import com.projectbyanuj.Secure_Journal_Application.journal_apis.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
}

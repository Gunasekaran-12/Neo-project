// src/main/java/com/examly/springapp/repository/BorrowerRepository.java
package com.examly.springapp.repository;

import com.examly.springapp.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    List<Borrower> findByNameContainingIgnoreCase(String name);
    Borrower findByEmail(String email);
}
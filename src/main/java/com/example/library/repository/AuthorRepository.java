package com.example.library.repository;

import com.example.library.model.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Writer, Long> {
}

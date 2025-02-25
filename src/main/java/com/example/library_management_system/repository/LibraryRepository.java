package com.example.library_management_system.repository;

import com.example.library_management_system.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    Optional<Library> findByName(String libraryName);
}

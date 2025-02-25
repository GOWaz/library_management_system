package com.example.library_management_system.repository;

import com.example.library_management_system.entity.LibraryCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryCardRepository extends JpaRepository<LibraryCard, Long> {
}

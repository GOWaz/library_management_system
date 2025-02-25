package com.example.library_management_system.repository;

import com.example.library_management_system.entity.LibraryMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibraryMemberRepository extends JpaRepository<LibraryMember, Long> {

    Optional<LibraryMember> findByEmail(String email);
}

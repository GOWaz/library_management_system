package com.example.library_management_system.service;

import com.example.library_management_system.entity.LibraryMember;
import com.example.library_management_system.models.LibraryMemberRequest;
import com.example.library_management_system.repository.LibraryMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryMemberService {
    private final LibraryMemberRepository libraryMemberRepository;

    public LibraryMemberService(LibraryMemberRepository libraryMemberRepository) {
        this.libraryMemberRepository = libraryMemberRepository;
    }

    public List<LibraryMember> findAll() {
        return libraryMemberRepository.findAll();
    }

    public Optional<LibraryMember> findById(Long id) {
        if (libraryMemberRepository.existsById(id)) {
            return libraryMemberRepository.findById(id);
        } else {
            throw new RuntimeException("Library member with id " + id + " not found");
        }
    }

    public LibraryMember create(LibraryMemberRequest request) {
        if (libraryMemberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Library member with email " + request.getEmail() + " already exists");
        }
        LibraryMember libraryMember = new LibraryMember();
        libraryMember.setName(request.getName());
        libraryMember.setEmail(request.getEmail());
        return libraryMemberRepository.save(libraryMember);
    }

    public LibraryMember update(LibraryMemberRequest request, Long id) {
        return libraryMemberRepository.findById(id).map(
                libraryMember -> {
                    libraryMember.setName(request.getName());
                    if (libraryMemberRepository.findByEmail(request.getEmail()).isPresent()) {
                        throw new RuntimeException("Library member with email " + request.getEmail() + " already exists");
                    }
                    libraryMember.setEmail(request.getEmail());
                    return libraryMemberRepository.save(libraryMember);
                }
        ).orElseThrow(() -> new RuntimeException("Library member with id " + id + " not found"));
    }

    public void delete(Long id) {
        if (libraryMemberRepository.existsById(id)) {
            libraryMemberRepository.deleteById(id);
        } else {
            throw new RuntimeException("Library member with id " + id + " not found");
        }
    }
}

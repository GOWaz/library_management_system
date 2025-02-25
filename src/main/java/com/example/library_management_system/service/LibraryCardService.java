package com.example.library_management_system.service;

import com.example.library_management_system.entity.Library;
import com.example.library_management_system.entity.LibraryCard;
import com.example.library_management_system.models.LibraryCardRequest;
import com.example.library_management_system.entity.LibraryMember;
import com.example.library_management_system.repository.LibraryCardRepository;
import com.example.library_management_system.repository.LibraryMemberRepository;
import com.example.library_management_system.repository.LibraryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryCardService {
    private final LibraryCardRepository libraryCardRepository;
    private final LibraryRepository libraryRepository;
    private final LibraryMemberRepository libraryMemberRepository;

    public LibraryCardService(LibraryCardRepository libraryCardRepository, LibraryRepository libraryRepository, LibraryMemberRepository libraryMemberRepository) {
        this.libraryCardRepository = libraryCardRepository;
        this.libraryRepository = libraryRepository;
        this.libraryMemberRepository = libraryMemberRepository;
    }

    public List<LibraryCard> getAll() {
        return libraryCardRepository.findAll();
    }

    public Optional<LibraryCard> getById(Long id) {
        if (libraryCardRepository.existsById(id)) {
            return libraryCardRepository.findById(id);
        }
        throw new RuntimeException("LibraryCard with id " + id + " not found");
    }

//    public LibraryCard create(LibraryCardRequest request) {
//        LibraryCard libraryCard = new LibraryCard(null, LocalDate.now(), null, null);
//        if (libraryRepository.findById(request.getLibrary_id()).isPresent()) {
//            Library library = libraryRepository.findById(request.getLibrary_id()).orElseThrow(() -> new RuntimeException("Library with id " + request.getLibrary_id() + " not found"));
//            libraryCard.setLibrary(library);
//        }
//        if (libraryMemberRepository.findById(request.getLibraryMember_id()).isPresent()) {
//            LibraryMember libraryMember = libraryMemberRepository.findById(request.getLibraryMember_id()).orElseThrow(() -> new RuntimeException("LibraryMember with id " + request.getLibraryMember_id() + " not found"));
//            libraryCard.setLibraryMember(libraryMember);
//        }
//        return libraryCardRepository.save(libraryCard);
//    }

    public LibraryCard create(Long libraryId, Long libraryMemberId) {
        LibraryCard libraryCard = new LibraryCard(null, LocalDate.now(), null, null);
        if (libraryRepository.findById(libraryId).isPresent()) {
            Library library = libraryRepository.findById(libraryId).orElseThrow(() -> new RuntimeException("Library with id " + libraryId + " not found"));
            libraryCard.setLibrary(library);
        }
        if (libraryMemberRepository.findById(libraryMemberId).isPresent()) {
            LibraryMember libraryMember = libraryMemberRepository.findById(libraryMemberId).orElseThrow(() -> new RuntimeException("LibraryMember with id " + libraryMemberId + " not found"));
            libraryCard.setLibraryMember(libraryMember);
        }
        return libraryCardRepository.save(libraryCard);
    }

    public void delete(Long id) {
        if (libraryCardRepository.existsById(id)) {
            libraryCardRepository.deleteById(id);
        } else {
            throw new RuntimeException("LibraryCard with id " + id + " not found");
        }
    }
}

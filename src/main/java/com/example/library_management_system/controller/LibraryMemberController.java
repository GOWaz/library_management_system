package com.example.library_management_system.controller;

import com.example.library_management_system.entity.LibraryMember;
import com.example.library_management_system.models.LibraryMemberRequest;
import com.example.library_management_system.service.LibraryMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/library-members")
public class LibraryMemberController {
    private final LibraryMemberService libraryMemberService;

    @Autowired
    public LibraryMemberController(LibraryMemberService libraryMemberService) {
        this.libraryMemberService = libraryMemberService;
    }

    @GetMapping
    public List<LibraryMember> getAllLibraryMembers() {
        return libraryMemberService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryMember> getLibraryMemberById(@PathVariable Long id) {
        Optional<LibraryMember> libraryMember = libraryMemberService.findById(id);
        return libraryMember.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LibraryMember> createLibraryMember(@RequestBody LibraryMemberRequest request) {
        LibraryMember libraryMember = libraryMemberService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryMember);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibraryMember> updateLibraryMember(@PathVariable Long id, @RequestBody LibraryMemberRequest request) {
        try {
            LibraryMember libraryMember = libraryMemberService.update(request, id);
            return ResponseEntity.ok(libraryMember);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibraryMember(@PathVariable Long id) {
        libraryMemberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

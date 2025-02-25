package com.example.library_management_system.controller;

import com.example.library_management_system.entity.LibraryCard;
import com.example.library_management_system.models.LibraryCardRequest;
import com.example.library_management_system.service.LibraryCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/library-cards")
public class LibraryCardController {
    private final LibraryCardService libraryCardService;

    @Autowired
    public LibraryCardController(LibraryCardService libraryCardService) {
        this.libraryCardService = libraryCardService;
    }

    @GetMapping
    public List<LibraryCard> getAll() {
        return libraryCardService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryCard> getById(@PathVariable Long id) {
        Optional<LibraryCard> libraryCard = libraryCardService.getById(id);
        return libraryCard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LibraryCard> create(@RequestParam Long libraryId,
                                              @RequestParam Long libraryMemberId) {
//        LibraryCard libraryCard = libraryCardService.create(request);
        LibraryCard libraryCard = libraryCardService.create(libraryId,libraryMemberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryCard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        libraryCardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

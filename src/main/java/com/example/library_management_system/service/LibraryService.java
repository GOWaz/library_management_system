package com.example.library_management_system.service;

import com.example.library_management_system.entity.Library;
import com.example.library_management_system.repository.LibraryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public List<Library> getAll() {
        return libraryRepository.findAll();
    }

    public Optional<Library> getById(Long id) {
        if (libraryRepository.existsById(id)) {
            return libraryRepository.findById(id);
        } else {
            throw new RuntimeException("Library with the id " + id + " not found");
        }
    }

    public Library create(String name) {
        if (libraryRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Library with the name " + name + " already exists");
        }
        Library library = new Library();
        library.setName(name);
        return libraryRepository.save(library);
    }

    public Library update(Long id, String name) {
        return libraryRepository.findById(id).map(
                updatedLibrary -> {
                    if (libraryRepository.findByName(name).isPresent()) {
                        throw new RuntimeException("Library with the name " + updatedLibrary.getName() + " already exists");
                    }
                    updatedLibrary.setName(name);
                    return libraryRepository.save(updatedLibrary);
                }
        ).orElseThrow(() -> new RuntimeException("Library with the id " + id + " not found"));
    }

    public void delete(Long id) {
        if (libraryRepository.existsById(id)) {
            libraryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Library with the id " + id + " not found");
        }
    }
}

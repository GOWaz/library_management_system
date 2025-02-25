package com.example.library_management_system.service;

import com.example.library_management_system.entity.Author;
import com.example.library_management_system.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAll() {
        return authorRepository.findAll();
    }


    public Optional<Author> getById(Long id) {
        if (authorRepository.existsById(id)) {
            return authorRepository.findById(id);
        } else {
            throw new RuntimeException("Author with id " + id + " not found");
        }
    }

    public Author create(Author author) {
        if (authorRepository.findByName(author.getName()).isPresent()) {
            throw new RuntimeException("Author with name " + author.getName() + " already exists");
        }
        return authorRepository.save(author);
    }

    public Author update(Long id, Author updateAuthor) {
        return authorRepository.findById(id).map(
                author -> {
                    if (!author.getName().equals(updateAuthor.getName())) {
                        author.setName(updateAuthor.getName());
                    }
                    author.setBio(updateAuthor.getBio());
                    return authorRepository.save(author);
                }
        ).orElseThrow(() -> new RuntimeException("Author with id " + id + " not found"));
    }

    public void delete(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Author with id " + id + " not found");
        }
    }
}

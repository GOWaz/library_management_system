package com.example.library_management_system.controller;

import com.example.library_management_system.entity.Book;
import com.example.library_management_system.models.BookRequest;
import com.example.library_management_system.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookRequest request) {
        Book book = bookService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookRequest request) {
        try {
            Book updatedBook = bookService.update(id, request);
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{bookId}/assignt-to-author/{authorId}")
    public ResponseEntity<Book> assignBookToAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
        Book book = bookService.assignAuthor(bookId, authorId);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{bookId}/assignt-to-author/{libraryId}")
    public ResponseEntity<Book> assignBookToLibrary(@PathVariable Long libraryId, @PathVariable Long bookId) {
        Book book = bookService.assignLibrary(bookId, libraryId);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{bookId}/assignt-to-author/{categoryId}")
    public ResponseEntity<Book> assignBookToCategory(@PathVariable Long categoryId, @PathVariable Long bookId) {
        Book book = bookService.assignCategory(bookId, categoryId);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

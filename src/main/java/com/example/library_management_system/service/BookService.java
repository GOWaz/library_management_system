package com.example.library_management_system.service;

import com.example.library_management_system.entity.Author;
import com.example.library_management_system.entity.Book;
import com.example.library_management_system.entity.Category;
import com.example.library_management_system.entity.Library;
import com.example.library_management_system.models.BookRequest;
import com.example.library_management_system.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;
    private final CategoryRepository categoryRepository;
    private final LoanRepository loanRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, LibraryRepository libraryRepository, CategoryRepository categoryRepository, LoanRepository loanRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
        this.categoryRepository = categoryRepository;
        this.loanRepository = loanRepository;
        this.authorRepository = authorRepository;
    }


    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> getById(Long id) {
        if (bookRepository.existsById(id)) {
            return bookRepository.findById(id);
        } else {
            throw new RuntimeException("Book with id " + id + " not found");
        }
    }

    public Book create(BookRequest request) {
        Book book = new Book();
        bookObject(request, book);
        if (request.getLibraryId() != null) {
            Library library = libraryRepository.findById(request.getLibraryId()).orElseThrow(() -> new RuntimeException("Library with id " + request.getLibraryId() + " not found"));
            book.setLibrary(library);
        }
        if (request.getCategoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());
            book.setCategories(categories);
        }
        if (request.getAuthorIds() != null) {
            List<Author> authors = authorRepository.findAllById(request.getAuthorIds());
            book.setAuthors(authors);
        }
        return bookRepository.save(book);
    }

    private void bookObject(BookRequest request, Book book) {
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setPublishedDate(request.getPublishedDate());
        book.setStatus(request.getStatus());
    }

    public Book update(Long id, BookRequest request) {
        return bookRepository.findById(id).map(
                updatedBook -> {
                    bookObject(request, updatedBook);
                    if (request.getAuthorIds() != null) {
                        List<Author> authors = authorRepository.findAllById(request.getAuthorIds());
                        updatedBook.setAuthors(authors);
                    }
                    if (request.getLibraryId() != null) {
                        Library library = libraryRepository.findById(request.getLibraryId()).orElse(null);
                        updatedBook.setLibrary(library);

                    }
                    if (request.getCategoryIds() != null) {
                        List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());
                        updatedBook.setCategories(categories);
                    }
                    return bookRepository.save(updatedBook);
                }
        ).orElseThrow(() -> new RuntimeException("Book with id " + id + " not found"));
    }

    public Book assignAuthor(Long bookId, Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException("Author with id " + authorId + " not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book with id " + bookId + " not found"));
        book.getAuthors().add(author);
        return bookRepository.save(book);
    }

    public Book assignLibrary(Long bookId, Long libraryId) {
        Library library = libraryRepository.findById(libraryId).orElseThrow(() -> new RuntimeException("Library with id " + libraryId + " not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book with id " + bookId + " not found"));
        book.setLibrary(library);
        return bookRepository.save(book);
    }

    public Book assignCategory(Long bookId, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category with id " + categoryId + " not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book with id " + bookId + " not found"));
        book.getCategories().add(category);
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new RuntimeException("Book with id " + id + " not found");
        }
    }
}

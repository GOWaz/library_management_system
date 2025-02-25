package com.example.library_management_system.service;

import com.example.library_management_system.entity.*;
import com.example.library_management_system.models.LoanRequest;
import com.example.library_management_system.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final LibraryCardRepository libraryCardRepository;
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;
    private final LibraryMemberRepository libraryMemberRepository;

    public LoanService(LoanRepository loanRepository, LibraryCardRepository libraryCardRepository, BookRepository bookRepository, LibraryRepository libraryRepository, LibraryMemberRepository libraryMemberRepository) {
        this.loanRepository = loanRepository;
        this.libraryCardRepository = libraryCardRepository;
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
        this.libraryMemberRepository = libraryMemberRepository;
    }

    public List<Loan> getAll() {
        return loanRepository.findAll();
    }

    public Optional<Loan> getById(Long id) {
        if (loanRepository.existsById(id)) {
            Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("No loan found with id: " + id));
            if (LocalDate.now().isAfter(loan.getReturnDate())) {
                loan.setStatus(LoanStatus.OVERDUE);
            }
            return loanRepository.findById(id);
        } else {
            throw new RuntimeException("No loan found with id: " + id);
        }
    }

    @Transactional
    public Loan create(LoanRequest request) {
        LibraryCard libraryCard = libraryCardRepository.findById(request.getLibraryCardId())
                .orElseThrow(() -> new RuntimeException("Library Card with ID " + request.getLibraryCardId() + " not found!"));

        Library library = libraryRepository.findById(libraryCard.getLibrary().getId())
                .orElseThrow(() -> new RuntimeException("Library with ID " + libraryCard.getLibrary().getId() + " not found!"));

        LibraryMember libraryMember = libraryMemberRepository.findById(libraryCard.getLibraryMember().getId())
                .orElseThrow(() -> new RuntimeException("Library member with ID " + libraryCard.getLibraryMember().getId() + " not found!"));

        List<Book> availableBooks = bookRepository.findByLibrary(library).stream()
                .filter(book -> book.getStatus() == BookStatus.AVAILABLE)
                .toList();

        List<Long> requestedBookIds = request.getBookIds();
        List<Book> booksToLoan = availableBooks.stream()
                .filter(book -> requestedBookIds.contains(book.getId()))
                .toList();

        if (booksToLoan.size() != requestedBookIds.size()) {
            throw new RuntimeException("Some requested books are not available.");
        }

        booksToLoan.forEach(book -> book.setStatus(BookStatus.BORROWED));
        bookRepository.saveAll(booksToLoan);

        Loan loan = new Loan();
        loan.setLibrary(library);
        loan.setLibraryMember(libraryMember);
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(LocalDate.now().plusMonths(3));
        loan.setStatus(LoanStatus.BORROWED);
        loan.setBooks(booksToLoan);

        return loanRepository.save(loan);
    }


    public Loan update(Long id, LoanStatus status) {
        return loanRepository.findById(id).map(
                loan -> {
                    if (status != null) {
                        loan.setStatus(status);
                    }
                    loan.setStatus(LoanStatus.RETURNED);
                    return loanRepository.save(loan);
                }
        ).orElseThrow(() -> new RuntimeException("No loan found with id: " + id));
    }

    public void delete(Long id) {
        if (loanRepository.existsById(id)) {
            loanRepository.deleteById(id);
        } else {
            throw new RuntimeException("No loan found with id: " + id);
        }
    }
}

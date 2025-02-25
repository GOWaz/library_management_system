package com.example.library_management_system.repository;

import com.example.library_management_system.entity.Book;
import com.example.library_management_system.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.title = :title")
    Optional<Book> findBookByTitle(@Param("title") String title);

    List<Book> findByLibrary(Library library);

}

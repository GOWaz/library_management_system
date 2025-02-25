package com.example.library_management_system.models;

import com.example.library_management_system.entity.BookStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BookRequest {
    private String title;
    private Integer isbn;
    private LocalDate publishedDate;
    private BookStatus status = BookStatus.AVAILABLE;
    private List<Long> authorIds;
    private Long libraryId;
    private List<Long> categoryIds;
}

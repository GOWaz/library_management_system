package com.example.library_management_system.models;

import com.example.library_management_system.entity.LoanStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LoanRequest {
    private Long libraryCardId;
    private List<Long> bookIds;
}

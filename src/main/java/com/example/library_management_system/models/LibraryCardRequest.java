package com.example.library_management_system.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryCardRequest {
    private Long library_id;
    private Long LibraryMember_id;
}

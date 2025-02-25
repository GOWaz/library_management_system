package com.example.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "LibraryCard")
@Table(name = "library_card")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LibraryCard {

    @Id
    @SequenceGenerator(
            name = "libraryCard_sequence"
            , sequenceName = "libraryCard_sequence"
            , allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "libraryCard_sequence"
    )
    private Long id;

//    @Column(name = "cardNumber",
//            nullable = false,
//            unique = true,
//            length = 10)
//    private String cardNumber;

    @Column(
            name = "membershipDate",
            nullable = false,
            columnDefinition = "DATE"
    )
    private LocalDate membershipDate;

    @ManyToOne
    @JoinColumn(name = "library_id")
    @JsonBackReference
    private Library library;

    @ManyToOne
    @JoinColumn(name = "LibraryMember_id")
    @JsonBackReference
    private LibraryMember libraryMember;
}

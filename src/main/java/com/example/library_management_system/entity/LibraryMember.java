package com.example.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "LibraryMember")
@Table(
        name = "libraryMember",
        uniqueConstraints = {
                @UniqueConstraint(name = "library_member_email_unique",
                        columnNames = "email")
        }
)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LibraryMember {

    @Id
    @SequenceGenerator(
            name = "libraryMember_sequence",
            sequenceName = "libraryMember_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "libraryMember_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String Name;

    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT"
//            unique = true
    )
    private String email;

    @OneToMany(mappedBy = "libraryMember", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private List<LibraryCard> libraryCard;

//    @ManyToMany
//    @JoinTable(
//            name = "member_library",
//            joinColumns = @JoinColumn(name = "member_id"),
//            inverseJoinColumns = @JoinColumn(name = "library_id")
//    )
//    private List<Library> libraries;

    @OneToMany(mappedBy = "libraryMember", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private List<Loan> loans;
}

package com.capstone.timeflow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "TERMS")
public class TermsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "termsId")
    private Long termsId;

    @Column(name = "termsName", nullable = false, length = 50)
    private String termsName;

    @Column(name = "termsContents", nullable = false, columnDefinition = "MEDIUMTEXT")
    private String termsContents;

    @Column(name = "termsEssential", nullable = false, length = 1)
    private String termsEssential;
}

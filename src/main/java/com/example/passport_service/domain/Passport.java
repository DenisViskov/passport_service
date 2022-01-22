package com.example.passport_service.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "passport")
@NoArgsConstructor
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial", nullable = false)
    private Long serial;

    @Column(name = "number", unique = true, nullable = false)
    private Long number;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Column(name = "issuing_authority", nullable = false)
    private String issuingAuthority;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_issue", nullable = false)
    private Date dateOfIssue;

    @Temporal(TemporalType.DATE)
    @Column(name = "expired_date", nullable = false)
    private Date expiredDate;
}

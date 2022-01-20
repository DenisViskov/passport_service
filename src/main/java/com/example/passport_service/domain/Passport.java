package com.example.passport_service.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "passport")
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial")
    private Long serial;

    @Column(name = "number", unique = true)
    private Long number;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "issuing_authority")
    private String issuingAuthority;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_issue")
    private Date dateOfIssue;

    @Temporal(TemporalType.DATE)
    @Column(name = "expired_date")
    private Date expiredDate;
}

package com.example.memotion.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "HELLO_USER")
public class HelloUser {
    @Id
    private Long id;
    private String yes;
}

package com.example.memotion.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "heaing")
public class Healing extends BaseEntity{
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String musicUrl;
}

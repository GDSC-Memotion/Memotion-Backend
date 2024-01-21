package com.example.memotion.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image")
@Getter
@NoArgsConstructor
public class Image extends BaseEntity{
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uri;

    public Image(String uri) {
        this.uri = uri;
    }
}

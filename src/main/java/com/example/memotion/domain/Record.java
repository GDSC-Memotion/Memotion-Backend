package com.example.memotion.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "record")
@NoArgsConstructor
public class Record extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;
    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    private MoodEnum mood;
    private String imageUrl;

    @Builder
    public Record(String description, Member member, MoodEnum mood, String imageUrl) {
        this.description = description;
        this.member = member;
        this.mood = mood;
        this.imageUrl = imageUrl;
    }
}

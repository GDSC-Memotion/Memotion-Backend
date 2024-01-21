package com.example.memotion.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "record_image")
@Getter
@NoArgsConstructor
public class RecordImage extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "record_id")
    private Record record;
    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    public RecordImage(Record record, Image image) {
        this.record = record;
        this.image = image;
    }
}

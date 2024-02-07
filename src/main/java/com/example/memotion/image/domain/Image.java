package com.example.memotion.image.domain;

import com.example.memotion.common.domain.BaseEntity;
import com.example.memotion.reecord.domain.Diary;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "image")
@Getter
@Setter
@NoArgsConstructor
public class Image extends BaseEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uri;
    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    public Image(String uri, Diary diary) {
        this.uri = uri;
        this.diary = diary;
    }
}

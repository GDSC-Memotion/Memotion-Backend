package com.example.memotion.heealing.domain;

import com.example.memotion.common.domain.BaseEntity;
import com.example.memotion.reecord.domain.Diary;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "healing_diary")
public class HealingDiary extends BaseEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;
    @ManyToOne
    @JoinColumn(name = "healing_id")
    private Healing healing;
}

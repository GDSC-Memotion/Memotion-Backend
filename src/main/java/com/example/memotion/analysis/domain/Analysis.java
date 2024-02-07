package com.example.memotion.analysis.domain;

import com.example.memotion.common.domain.BaseEntity;
import com.example.memotion.member.domain.Member;
import com.example.memotion.reecord.domain.Diary;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "analysis")
public class Analysis extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Column(nullable = false)
    private float joy;
    private float neutral;
    private float sadness;
    private float surprise;
    private float anger;
    private float fear;
    private float disgust;
}

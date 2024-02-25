package com.example.memotion.reecord.domain;

import com.example.memotion.common.domain.STATUS;
import com.example.memotion.image.domain.Image;
import com.example.memotion.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "diary")
@ToString
@NoArgsConstructor
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    @OneToMany(mappedBy = "diary")
    private List<Image> images;

    @Enumerated(value = EnumType.STRING)
    private STATUS status = STATUS.ACTIVATE;

    @Builder
    public Diary(Long id, String description, Member member, LocalDateTime createdAt) {
        this.id = id;
        this.description = description;
        this.member = member;
        this.setCreatedAt(createdAt);
    }

    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}

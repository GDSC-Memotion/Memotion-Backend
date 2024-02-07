package com.example.memotion.heealing.repository;

import com.example.memotion.heealing.domain.Healing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealingRepository extends JpaRepository<Healing, Long> {
}

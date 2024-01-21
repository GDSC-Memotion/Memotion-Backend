package com.example.memotion.repository;

import com.example.memotion.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member,Long> {
}

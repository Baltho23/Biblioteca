package com.biblioteca.backend.repository;

import com.biblioteca.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByEmail(String email);
}

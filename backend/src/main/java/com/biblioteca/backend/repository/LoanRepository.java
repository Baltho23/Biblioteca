package com.biblioteca.backend.repository;

import com.biblioteca.backend.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Long countByMemberIdAndReturnDateIsNull(Long memberId);
    List<Loan> findByReturnDateIsNull();
    List<Loan> findByDueDateBeforeAndReturnDateIsNull(LocalDate date);
}

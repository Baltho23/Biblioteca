package com.biblioteca.backend.repository;

import com.biblioteca.backend.dto.BookStatsDto;
import com.biblioteca.backend.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Long countByMemberIdAndReturnDateIsNull(Long memberId);
    List<Loan> findByReturnDateIsNull();
    List<Loan> findByDueDateBeforeAndReturnDateIsNull(LocalDate date);
    @Query("SELECT new com.biblioteca.backend.dto.BookStatsDto(b.id, b.title, COUNT(l)) " +
            "FROM Loan l JOIN l.book b " +
            "GROUP BY b.id, b.title " +
            "ORDER BY COUNT(l) DESC")
    List<BookStatsDto> findBooksByLoanCountDesc();

}

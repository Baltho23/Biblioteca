package com.biblioteca.backend.service;

import com.biblioteca.backend.dto.BookStatsDto;
import com.biblioteca.backend.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class StatisticsService {
    private final LoanRepository loanRepository;

    public StatisticsService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<BookStatsDto> getTopAndLeastLoanedBooks() {
        List<BookStatsDto> allBooks = loanRepository.findBooksByLoanCountDesc();
        if (allBooks.isEmpty()) return Collections.emptyList();

        BookStatsDto mostLoaned = allBooks.getFirst();
        BookStatsDto leastLoaned = allBooks.getLast();

        return List.of(mostLoaned, leastLoaned);
    }
}

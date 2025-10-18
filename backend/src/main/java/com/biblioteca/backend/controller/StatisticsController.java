package com.biblioteca.backend.controller;

import com.biblioteca.backend.dto.BookStatsDto;
import com.biblioteca.backend.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/books-loans")
    public ResponseEntity<List<BookStatsDto>> getBookLoanStats() {
        return ResponseEntity.ok(statisticsService.getTopAndLeastLoanedBooks());
    }
}


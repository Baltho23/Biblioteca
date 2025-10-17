package com.biblioteca.backend.controller;

import com.biblioteca.backend.dto.LoanSaveDto;
import com.biblioteca.backend.entity.Loan;
import com.biblioteca.backend.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAll() {
        List<Loan> loans = loanService.findAll();

        if (loans.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/{id}")
    public Loan getById(@PathVariable Long id) {
        return loanService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Loan> create(@Valid @RequestBody LoanSaveDto loanSaveDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.create(loanSaveDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Loan> update(@PathVariable Long id, @Valid @RequestBody LoanSaveDto loanSaveDto) {
        return  ResponseEntity.ok(loanService.update(id, loanSaveDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        Boolean deleted = loanService.delete(id);

        if (deleted) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

    @PutMapping("/{id}/return")
    public Loan returnLoan(@PathVariable Long id) {
        return loanService.returnLoan(id);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Loan>> getActiveLoans() {
        List<Loan> loans = loanService.findActiveLoans();

        if (loans.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Loan>> getOverdueLoans() {
        List<Loan> loans = loanService.findOverdueLoans();

        if (loans.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(loans);
    }
}

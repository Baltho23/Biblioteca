package com.biblioteca.backend.service;

import com.biblioteca.backend.dto.LoanSaveDto;
import com.biblioteca.backend.dto.LoanUpdateDto;
import com.biblioteca.backend.entity.Book;
import com.biblioteca.backend.entity.Loan;
import com.biblioteca.backend.entity.Member;
import com.biblioteca.backend.repository.BookRepository;
import com.biblioteca.backend.repository.LoanRepository;
import com.biblioteca.backend.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public Loan findById(Long id) {
        return loanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado con id: " + id));
    }

    public Loan create(LoanSaveDto loanSaveDto) {
        Book bookFind = bookRepository.findById(loanSaveDto.bookId()).orElseThrow(() -> new EntityNotFoundException("Libro no encontrado con id: " + loanSaveDto.bookId()));
        Member memberFind = memberRepository.findById(loanSaveDto.memberId()).orElseThrow(() -> new EntityNotFoundException("miembro no encontrado con id: " + loanSaveDto.memberId()));

        if (bookFind.getCopiesAvailable() <= 0){
            throw new IllegalStateException("No hay copias disponibles del libro: " + bookFind.getTitle());
        }

        Long activeLoans = loanRepository.countByMemberIdAndReturnDateIsNull(loanSaveDto.memberId());
        if (activeLoans >= 3){
            throw new IllegalStateException("El miembro ya tiene el máximo de préstamos activos permitidos (3).");
        }

        Loan loan = Loan.builder()
                .book(bookFind)
                .member(memberFind)
                .loanDate(loanSaveDto.loanDate())
                .dueDate(loanSaveDto.dueDate())
                .returnDate(null)
                .build();

        bookFind.setCopiesAvailable(bookFind.getCopiesAvailable() - 1);
        bookRepository.save(bookFind);

        return loanRepository.save(loan);
    }

    public Loan update(Long id, LoanUpdateDto loanUpdateDto) {
        Loan loanFind = loanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Loan no encontrado con id: " + id));
        Book bookFind = bookRepository.findById(loanUpdateDto.bookId()).orElseThrow(() -> new EntityNotFoundException("Libro no encontrado con id: " + loanUpdateDto.bookId()));
        Member memberFind = memberRepository.findById(loanUpdateDto.memberId()).orElseThrow(() -> new EntityNotFoundException("Miembro no encontrado con id: " + loanUpdateDto.memberId()));

        if (loanFind.getReturnDate() == null && loanUpdateDto.returnDate() != null){
            bookFind.setCopiesAvailable(bookFind.getCopiesAvailable() + 1);
            bookRepository.save(bookFind);
        }

        loanFind.setBook(bookFind);
        loanFind.setMember(memberFind);
        loanFind.setLoanDate(loanUpdateDto.loanDate());
        loanFind.setDueDate(loanUpdateDto.dueDate());
        loanFind.setReturnDate(loanUpdateDto.returnDate());

        return loanRepository.save(loanFind);
    }

    public Loan returnLoan(Long loanId){
        Loan loanFind = loanRepository.findById(loanId).orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado con id: " + loanId));

        if (loanFind.getReturnDate() != null){
            throw new IllegalStateException("Este préstamo ya fue devuelto.");
        }
        loanFind.setReturnDate(LocalDate.now());

        Book book = loanFind.getBook();
        book.setCopiesAvailable(book.getCopiesAvailable() + 1);
        bookRepository.save(book);

        return loanRepository.save(loanFind);
    }

    public Boolean delete(Long id) {
        if (loanRepository.existsById(id)) {
            loanRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Loan> findActiveLoans() {
        return loanRepository.findByReturnDateIsNull();
    }

    public List<Loan> findOverdueLoans() {
        LocalDate today = LocalDate.now();
        return loanRepository.findByDueDateBeforeAndReturnDateIsNull(today);
    }
}

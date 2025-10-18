package com.biblioteca.backend;

import com.biblioteca.backend.dto.LoanSaveDto;
import com.biblioteca.backend.entity.Book;
import com.biblioteca.backend.entity.Member;
import com.biblioteca.backend.repository.BookRepository;
import com.biblioteca.backend.repository.LoanRepository;
import com.biblioteca.backend.repository.MemberRepository;
import com.biblioteca.backend.service.LoanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private LoanService loanService;

    @Test
    public void createLoan_noCopiesAvailable_throwsException() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Libro A");
        book.setCopiesAvailable(0);

        Member member = new Member();
        member.setId(1L);

        LoanSaveDto dto = new LoanSaveDto(1L, 1L, LocalDate.now(), LocalDate.now().plusDays(7), null);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            loanService.create(dto);
        });

        assertEquals("No hay copias disponibles del libro: Libro A", exception.getMessage());
    }
}


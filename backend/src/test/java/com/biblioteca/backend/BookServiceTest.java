package com.biblioteca.backend;

import com.biblioteca.backend.dto.BookSaveDto;
import com.biblioteca.backend.entity.Book;
import com.biblioteca.backend.repository.BookRepository;
import com.biblioteca.backend.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void createBook_success() {

        BookSaveDto bookSaveDto = new BookSaveDto("Libro A", "Autor", "Género", 3);

        Book book = Book.builder()
                .title(bookSaveDto.title())
                .author(bookSaveDto.author())
                .genre(bookSaveDto.genre())
                .copiesAvailable(bookSaveDto.copiesAvailable())
                .build();

        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        Book savedBook = bookService.create(bookSaveDto);
        
        assertNotNull(savedBook);
        assertEquals("Libro A", savedBook.getTitle());
        assertEquals(3, savedBook.getCopiesAvailable());
        Mockito.verify(bookRepository, Mockito.times(1)).save(Mockito.any(Book.class));
    }
}



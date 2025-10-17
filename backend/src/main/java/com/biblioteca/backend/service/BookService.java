package com.biblioteca.backend.service;

import com.biblioteca.backend.dto.BookSaveDto;
import com.biblioteca.backend.entity.Book;
import com.biblioteca.backend.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Libro no encontrado con id: " + id));
    }

    public Book create(BookSaveDto bookSaveDto) {
        Book book = Book.builder()
                .title(bookSaveDto.title())
                .author(bookSaveDto.author())
                .genre(bookSaveDto.genre())
                .copiesAvailable(bookSaveDto.copiesAvailable())
                .build();
        return bookRepository.save(book);
    }

    public Book update(Long id, BookSaveDto bookSaveDto) {
        Book bookFind = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book no encontrado con id: " + id));

        bookFind.setTitle(bookSaveDto.title());
        bookFind.setAuthor(bookSaveDto.author());
        bookFind.setGenre(bookSaveDto.genre());
        bookFind.setCopiesAvailable(bookSaveDto.copiesAvailable());

        return bookRepository.save(bookFind);
    }

    public Boolean delete(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<Book> findByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    public void updateCopiesAvailable(Long id, Integer copies) {
        Book bookFind = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book no encontrado con id: " + id));

        if(copies < 0){
            throw new IllegalArgumentException("Las copias no pueden ser negativas");
        }
        bookFind.setCopiesAvailable(copies);
        bookRepository.save(bookFind);
    }
}

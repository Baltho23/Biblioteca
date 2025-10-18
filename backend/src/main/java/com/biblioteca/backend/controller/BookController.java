package com.biblioteca.backend.controller;

import com.biblioteca.backend.dto.BookSaveDto;
import com.biblioteca.backend.entity.Book;
import com.biblioteca.backend.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        List<Book> books = bookService.findAll();

        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Book> create(@Valid @RequestBody BookSaveDto bookSaveDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(bookSaveDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id,@Valid @RequestBody BookSaveDto bookSaveDto) {
        return  ResponseEntity.ok(bookService.update(id, bookSaveDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        Boolean deleted = bookService.delete(id);

        if (deleted) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> getByAuthor(@PathVariable String author) {
        List<Book> books = bookService.findByAuthor(author);

        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Book>> getByGenre(@PathVariable String genre) {
        List<Book> books = bookService.findByGenre(genre);

        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }

    @PatchMapping("/{id}/copies/{copies}")
    public ResponseEntity<String> updateCopies(@PathVariable Long id, @PathVariable Integer copies) {
        try {
            bookService.updateCopiesAvailable(id, copies);
            return ResponseEntity.ok("Copias actualizadas correctamente");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }
}

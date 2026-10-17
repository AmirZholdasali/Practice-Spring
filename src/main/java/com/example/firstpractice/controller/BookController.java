package com.example.firstpractice.controller;

import com.example.firstpractice.dto.BookRequest;
import com.example.firstpractice.dto.BookResponse;
import com.example.firstpractice.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import java.net.URI;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) { this.service = service; }

    @GetMapping
    public PagedModel<BookResponse> all(Pageable pageable) {
        return new PagedModel<>(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public BookResponse one(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest req) {
        BookResponse created = service.create(req);
        return ResponseEntity.created(URI.create("/books/" + created.id())).body(created); // 201 + Location
    }

    @PutMapping("/{id}")
    public BookResponse update(@PathVariable Long id, @Valid @RequestBody BookRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void delete(@PathVariable Long id) { service.delete(id); }
}

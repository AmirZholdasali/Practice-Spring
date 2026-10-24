package com.example.firstpractice.controller;

import com.example.firstpractice.domain.BookModel;
import com.example.firstpractice.dto.BookRequest;
import com.example.firstpractice.dto.BookResponse;
import com.example.firstpractice.mapper.BookMapper;
import com.example.firstpractice.service.BookService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;
    private final BookMapper mapper;

    public BookController(BookService service, BookMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public PagedModel<BookResponse> all(Pageable pageable) {
        return new PagedModel<>(service.findAll(pageable).map(mapper::toResponse));
    }

    @GetMapping("/{id}")
    public BookResponse one(@PathVariable Long id) {
        return mapper.toResponse(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest req) {
        BookModel created = service.create(mapper.fromRequest(req));
        return ResponseEntity.created(URI.create("/books/" + created.id()))
                .body(mapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public BookResponse update(@PathVariable Long id, @Valid @RequestBody BookRequest req) {
        return mapper.toResponse(service.update(id, mapper.fromRequest(req)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
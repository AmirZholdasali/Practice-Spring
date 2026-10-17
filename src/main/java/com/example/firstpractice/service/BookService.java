package com.example.firstpractice.service;

import com.example.firstpractice.dto.BookRequest;
import com.example.firstpractice.dto.BookResponse;
import com.example.firstpractice.entity.Book;
import com.example.firstpractice.exception.ResourceNotFoundException;
import com.example.firstpractice.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository repo;

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    public Page<BookResponse> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    public BookResponse findById(Long id) {
        return repo.findById(id).map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Book " + id + " not found"));
    }

    public BookResponse create(BookRequest r) {
        return toResponse(repo.save(new Book(r.title(), r.pages())));
    }

    public BookResponse update(Long id, BookRequest r) {
        Book book = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book " + id + " not found"));
        book.setTitle(r.title());
        book.setPages(r.pages());
        return toResponse(repo.save(book));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Book " + id + " not found");
        }
        repo.deleteById(id);
    }

    private BookResponse toResponse(Book b) {
        return new BookResponse(b.getId(), b.getTitle(), b.getPages());
    }
}
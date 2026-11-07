package com.example.firstpractice.service;

import com.example.firstpractice.domain.BookModel;
import com.example.firstpractice.entity.Book;
import com.example.firstpractice.exception.ResourceNotFoundException;
import com.example.firstpractice.mapper.BookMapper;
import com.example.firstpractice.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository repo;
    private final BookMapper mapper;

    public BookService(BookRepository repo, BookMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public Page<BookModel> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::fromEntity);
    }

    @Cacheable(value = "books", key = "#id")
    public BookModel findById(Long id) {
        return repo.findById(id).map(mapper::fromEntity)
                .orElseThrow(() -> notFound(id));
    }

    public BookModel create(BookModel model) {
        return mapper.fromEntity(repo.save(mapper.toEntity(model)));
    }

    @CacheEvict(value = "books", key = "#id")
    public BookModel update(Long id, BookModel model) {
        Book book = repo.findById(id).orElseThrow(() -> notFound(id));
        book.setTitle(model.title());
        book.setPages(model.pages());
        return mapper.fromEntity(repo.save(book));
    }

    @CacheEvict(value = "books", key = "#id")
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw notFound(id);
        }
        repo.deleteById(id);
    }

    private ResourceNotFoundException notFound(Long id) {
        return new ResourceNotFoundException("Book " + id + " not found");
    }
}
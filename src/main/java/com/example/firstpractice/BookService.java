package com.example.firstpractice;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {
    private final Map<Long, BookResponse> books = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public List<BookResponse> findAll() { return List.copyOf(books.values()); }

    public BookResponse findById(Long id) {
        BookResponse book = books.get(id);
        if (book == null) throw new ResourceNotFoundException("Book " + id + " not found");
        return book;
    }

    public BookResponse create(BookRequest r) {
        BookResponse book = new BookResponse(nextId.getAndIncrement(), r.title(), r.pages());
        books.put(book.id(), book);
        return book;
    }

    public BookResponse update(Long id, BookRequest r) {
        findById(id);
        BookResponse book = new BookResponse(id, r.title(), r.pages());
        books.put(id, book);
        return book;
    }

    public void delete(Long id) {
        findById(id);
        books.remove(id);
    }
}

package com.example.firstpractice.mapper;

import com.example.firstpractice.domain.BookModel;
import com.example.firstpractice.dto.BookRequest;
import com.example.firstpractice.dto.BookResponse;
import com.example.firstpractice.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookModel fromRequest(BookRequest r) {          // DTO -> Domain
        return new BookModel(null, r.title(), r.pages());
    }

    public BookModel fromEntity(Book b) {                  // Entity -> Domain
        return new BookModel(b.getId(), b.getTitle(), b.getPages());
    }

    public Book toEntity(BookModel m) {                    // Domain -> Entity (DB will give id)
        return new Book(m.title(), m.pages());
    }

    public BookResponse toResponse(BookModel m) {          // Domain -> DTO
        return new BookResponse(m.id(), m.title(), m.pages());
    }
}
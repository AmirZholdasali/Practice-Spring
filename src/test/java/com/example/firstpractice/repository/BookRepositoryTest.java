package com.example.firstpractice.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.example.firstpractice.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    BookRepository repo;

    @Test
    void save_assignsIdFromDatabase() {
        Book saved = repo.save(new Book("Integration book", 10));

        assertNotNull(saved.getId());
        assertTrue(repo.findById(saved.getId()).isPresent());
    }

    @Test
    void findByTitleContainingIgnoreCase_findsBookIgnoringCase() {
        repo.save(new Book("Dune Messiah Test", 336));

        Page<Book> result = repo.findByTitleContainingIgnoreCase("dune messiah", PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        assertEquals("Dune Messiah Test", result.getContent().get(0).getTitle());
    }

    @Test
    void findAll_isPaged() {
        Page<Book> page = repo.findAll(PageRequest.of(0, 2));

        assertEquals(2, page.getContent().size());
        assertTrue(page.getTotalElements() >= 2);
    }
}
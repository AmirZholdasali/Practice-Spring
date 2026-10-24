package com.example.firstpractice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.firstpractice.domain.BookModel;
import com.example.firstpractice.entity.Book;
import com.example.firstpractice.exception.ResourceNotFoundException;
import com.example.firstpractice.mapper.BookMapper;
import com.example.firstpractice.repository.BookRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository repo;

    BookService service;

    @BeforeEach
    void setUp() {
        service = new BookService(repo, new BookMapper());
    }

    private Book bookWithId(Long id, String title, int pages) {
        Book b = new Book(title, pages);
        ReflectionTestUtils.setField(b, "id", id);
        return b;
    }

    @Test
    void findById_returnsModel_whenExists() {
        when(repo.findById(1L)).thenReturn(Optional.of(bookWithId(1L, "Dune", 412)));

        BookModel result = service.findById(1L);

        assertEquals(1L, result.id());
        assertEquals("Dune", result.title());
    }

    @Test
    void findById_throws_whenMissing() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void create_savesAndReturnsModelWithId() {
        when(repo.save(any(Book.class))).thenReturn(bookWithId(6L, "Test", 100));

        BookModel result = service.create(new BookModel(null, "Test", 100));

        assertEquals(6L, result.id());
        verify(repo).save(any(Book.class));
    }

    @Test
    void update_changesFields() {
        Book existing = bookWithId(1L, "Dune", 412);
        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(existing)).thenReturn(existing);

        BookModel result = service.update(1L, new BookModel(null, "Dune 2", 450));

        assertEquals("Dune 2", result.title());
        assertEquals(450, existing.getPages());
    }

    @Test
    void delete_throws_whenMissing() {
        when(repo.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(99L));
        verify(repo, never()).deleteById(any());
    }

    @Test
    void delete_deletes_whenExists() {
        when(repo.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repo).deleteById(1L);
    }
}
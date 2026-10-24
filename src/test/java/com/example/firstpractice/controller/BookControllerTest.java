package com.example.firstpractice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.firstpractice.domain.BookModel;
import com.example.firstpractice.exception.ResourceNotFoundException;
import com.example.firstpractice.mapper.BookMapper;
import com.example.firstpractice.service.BookService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
@Import(BookMapper.class)
class BookControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    BookService service;

    @Test
    void getOne_returns200() throws Exception {
        when(service.findById(1L)).thenReturn(new BookModel(1L, "Dune", 412));

        mvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Dune"));
    }

    @Test
    void getOne_missing_returns404_inUnifiedFormat() throws Exception {
        when(service.findById(99L)).thenThrow(new ResourceNotFoundException("Book 99 not found"));

        mvc.perform(get("/books/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Book 99 not found"));
    }

    @Test
    void post_valid_returns201WithLocation() throws Exception {
        when(service.create(any(BookModel.class))).thenReturn(new BookModel(6L, "Test", 100));

        mvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test\",\"pages\":100}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/books/6"))
                .andExpect(jsonPath("$.id").value(6));
    }

    @Test
    void post_invalid_returns400_andServiceNotCalled() throws Exception {
        mvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\",\"pages\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));

        verifyNoInteractions(service);
    }

    @Test
    void delete_returns204() throws Exception {
        mvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());

        verify(service).delete(1L);
    }

    @Test
    void getAll_returnsPage() throws Exception {
        var page = new PageImpl<>(List.of(new BookModel(1L, "Dune", 412)), PageRequest.of(0, 3), 1);
        when(service.findAll(any())).thenReturn(page);

        mvc.perform(get("/books?page=0&size=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Dune"))
                .andExpect(jsonPath("$.page.totalElements").value(1));
    }
}
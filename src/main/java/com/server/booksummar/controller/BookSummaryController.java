package com.server.booksummar.controller;

import com.server.booksummar.dto.request.BookSummaryRequest;
import com.server.booksummar.dto.response.BookSummaryResponse;
import com.server.booksummar.repository.BookSummaryRepository;
import com.server.booksummar.service.BookSummaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookSummary")
public class BookSummaryController {

    @Autowired
    private BookSummaryRepository bookSummaryRepository;
    @Autowired
    private BookSummaryService bookSummaryService;

    @PostMapping
    public ResponseEntity<BookSummaryResponse> create(@RequestBody @Valid BookSummaryRequest bookSummaryRequest) {
        BookSummaryResponse bookSummaryResponse = bookSummaryService.create(bookSummaryRequest);
        return ResponseEntity.created(URI.create("/bookSummary" + bookSummaryResponse.getId())).body(bookSummaryResponse);
    }

    @GetMapping
    public ResponseEntity<List<BookSummaryResponse>> findAll() {
        return ResponseEntity.ok(bookSummaryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookSummaryResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookSummaryService.findById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<BookSummaryResponse> update(@RequestBody @Valid BookSummaryRequest bookSummaryRequest, @PathVariable UUID id) {
        return ResponseEntity.ok(bookSummaryService.update(bookSummaryRequest, id));
    }

    @GetMapping("/bookName/{bookName}")
    public ResponseEntity<List<BookSummaryResponse>> findByBookName(@PathVariable String bookName) {
        return ResponseEntity.ok(bookSummaryService.findByBookName(bookName));
    }

    @GetMapping("/bookAuthor/{bookAuthor}")
    public ResponseEntity<List<BookSummaryResponse>> findByBookAuthor(@PathVariable String bookAuthor) {
        return ResponseEntity.ok(bookSummaryService.findByBookAuthor(bookAuthor));
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<BookSummaryResponse>> findByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(bookSummaryService.findByUserId(userId));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable UUID id) {
        bookSummaryService.delete(id);
        ResponseEntity.ok();
    }
}
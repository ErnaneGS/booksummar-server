package com.server.booksummar.service;

import com.server.booksummar.domain.BookSummary;
import com.server.booksummar.domain.EmailDetails;
import com.server.booksummar.domain.User;
import com.server.booksummar.dto.request.BookSummaryRequest;
import com.server.booksummar.dto.response.BookSummaryResponse;
import com.server.booksummar.dto.response.UserResponse;
import com.server.booksummar.mapper.BookSummaryMapper;
import com.server.booksummar.repository.BookSummaryRepository;
import com.server.booksummar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookSummaryService {

    @Autowired
    private BookSummaryRepository bookSummaryRepository;

    @Autowired
    private BookSummaryMapper bookSummaryMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    public BookSummaryResponse create(BookSummaryRequest bookSummaryRequest, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Nenhum usuário encontrado com o Id informado."));
        BookSummary bookSummary = bookSummaryMapper.bookSummaryRequestToBookSummary(bookSummaryRequest);
        bookSummary.setUser(user);
        bookSummaryRepository.save(bookSummary);
        return bookSummaryMapper.bookSummaryToBookSummaryResponse(bookSummary);
    }

    public List<BookSummaryResponse> findAll() {
        List<BookSummary> bookSummaries = bookSummaryRepository.findAll();
        return bookSummaries
                .stream()
                .map(bookSummaryMapper::bookSummaryToBookSummaryResponse)
                .collect(Collectors.toList());
    }

    public BookSummaryResponse findById(UUID idBookSummary) {
        BookSummary bookSummary = bookSummaryRepository.findById(idBookSummary)
                .orElseThrow(() -> new NoSuchElementException("Nenhum resumo encontrado com o Id informado."));
        return bookSummaryMapper.bookSummaryToBookSummaryResponse(bookSummary);
    }

    public BookSummaryResponse update(BookSummaryRequest bookSummaryRequest, UUID idBookSummary, UUID userId) {
        BookSummary bookSummary = bookSummaryRepository.findById(idBookSummary)
                .orElseThrow(() -> new NoSuchElementException("Nenhum resumo encontrado com o Id informado"));
        if (!bookSummary.getUser().getId().equals(userId)) {
            throw new RuntimeException("Operação permitida apenas para o autor deste resumo.");
        }
        bookSummaryMapper.bookSummaryUpdate(bookSummaryRequest, bookSummary);
        bookSummary = bookSummaryRepository.save(bookSummary);
        return bookSummaryMapper.bookSummaryToBookSummaryResponse(bookSummary);
    }

    public void delete(UUID idBookSummary) {
        if (bookSummaryRepository.existsById(idBookSummary)) {
            bookSummaryRepository.deleteById(idBookSummary);
        } else {
            throw new NoSuchElementException("Nenhum resumo encontrado com o Id informado.");
        }
    }

    public List<BookSummaryResponse> findByBookName(String bookName) {
        List<BookSummary> bookSummaries = bookSummaryRepository.findByBookNameContaining(bookName);

        if (bookSummaries.isEmpty()) {
            throw new NoSuchElementException("Nenhum resumo encontrado com o nome do livro informado.");
        }

        return bookSummaries.stream()
                .map(bookSummaryMapper::bookSummaryToBookSummaryResponse)
                .collect(Collectors.toList());
    }

    public List<BookSummaryResponse> findByBookAuthor(String bookAuthor) {
        List<BookSummary> bookSummaries = bookSummaryRepository.findByBookAuthorContaining(bookAuthor);

        if (bookSummaries.isEmpty()) {
            throw new NoSuchElementException("Nenhum resumo encontrado com o nome do autor informado.");
        }

        return bookSummaries.stream()
                .map(bookSummaryMapper::bookSummaryToBookSummaryResponse)
                .collect(Collectors.toList());
    }

    public List<BookSummaryResponse> findByUserId(UUID userId) {
        List<BookSummary> bookSummaries = bookSummaryRepository.findByUserId(userId);

        if (bookSummaries.isEmpty()) {
            throw new NoSuchElementException("Nenhum resumo encontrado com o id do usuario informado.");
        }

        return bookSummaries.stream()
                .map(bookSummaryMapper::bookSummaryToBookSummaryResponse)
                .collect(Collectors.toList());
    }

    public BookSummaryResponse sendSummaryEmail(UUID idBookSummary, UUID idUser) {
        BookSummary bookSummary = bookSummaryRepository.findById(idBookSummary)
                .orElseThrow(() -> new NoSuchElementException("Nenhum resumo encontrado com o Id informado."));

        UserResponse user = userService.findById(idUser);

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setSubject("BOOK FEEDBACK - COMPARTILHAMENTO DE FEEDBACK");
        emailDetails.setMessageBody("Nome do Livro: " + bookSummary.getBookName() + " \n Autor: " + bookSummary.getBookAuthor() + " \n Feedback: " + bookSummary.getSummary());
        emailDetails.setRecipient(user.getLogin());
        emailService.sendEmail(emailDetails);

        return bookSummaryMapper.bookSummaryToBookSummaryResponse(bookSummary);
    }
}
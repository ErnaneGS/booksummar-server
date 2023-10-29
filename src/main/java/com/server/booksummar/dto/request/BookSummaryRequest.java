package com.server.booksummar.dto.request;

import com.server.booksummar.domain.enums.BookCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class BookSummaryRequest {

    @NotBlank(message = "O campo 'título' não pode estar em branco.")
    @Size(min = 3, max = 30, message = "O campo 'título' deve ter entre 3 e 30 caracteres")
    private String title;

    @NotBlank(message = "O campo 'resumo' não pode estar em branco.")
    private String summary;

    @NotBlank(message = "O campo 'nome do livro' não pode estar em branco.")
    private String bookName;

    @NotBlank(message = "O campo 'autor do livro' não pode estar em branco.")
    private String bookAuthor;

    @NotBlank(message = "O campo 'nome do livro' não pode estar em branco.")
    private BookCategory bookCategory;

    @NotBlank(message = "O campo 'nome do livro' não pode estar em branco.")
    private String bookImage;

    private UUID userId;

}
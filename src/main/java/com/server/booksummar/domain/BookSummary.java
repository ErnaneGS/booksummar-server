package com.server.booksummar.domain;

import com.server.booksummar.domain.enums.BookCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class BookSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String summary;

    private String bookName;

    private String bookAuthor;

    private BookCategory bookCategory;

    private String bookImage;

    private UUID userId;

}
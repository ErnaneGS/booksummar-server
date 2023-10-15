package com.server.booksummar.domain;

import jakarta.persistence.*;
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

    private UUID userId;

}
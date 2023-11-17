package com.server.booksummar.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class BookSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String summary;

    private String bookName;

    private String bookAuthor;

    private String bookImage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
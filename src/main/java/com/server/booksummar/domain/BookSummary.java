package com.server.booksummar.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
public class BookSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String summary;

    private String bookName;

    private String bookAuthor;

    private String bookImage;

    private ZonedDateTime summaryDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
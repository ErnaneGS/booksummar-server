package com.server.booksummar.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class BookSummaryResponse {

    private UUID id;

    private String title;

    private String summary;

    private String bookName;

    private String bookAuthor;

    private UUID userId;

}
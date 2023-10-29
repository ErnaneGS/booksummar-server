package com.server.booksummar.dto.response;

import com.server.booksummar.domain.enums.BookCategory;
import lombok.Data;

import java.util.UUID;

@Data
public class BookSummaryResponse {

    private UUID id;

    private String title;

    private String summary;

    private String bookName;

    private String bookAuthor;

    private BookCategory bookCategory;

    private String bookImage;

    private UUID userId;

}
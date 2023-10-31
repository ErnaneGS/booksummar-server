package com.server.booksummar.mapper;

import com.server.booksummar.domain.BookSummary;
import com.server.booksummar.dto.request.BookSummaryRequest;
import com.server.booksummar.dto.response.BookSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookSummaryMapper {

    public BookSummary bookSummaryRequestToBookSummary(BookSummaryRequest bookSummaryRequest);

    public BookSummaryResponse bookSummaryToBookSummaryResponse(BookSummary bookSummary);

    public void bookSummaryUpdate(BookSummaryRequest bookSummaryRequest, @MappingTarget BookSummary bookSummary);

}

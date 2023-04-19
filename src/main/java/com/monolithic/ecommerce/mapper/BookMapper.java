package com.monolithic.ecommerce.mapper;


import com.monolithic.ecommerce.dtos.book.BookDTO;
import com.monolithic.ecommerce.dtos.book.BookRegistrationDTO;
import com.monolithic.ecommerce.mapper.config.MappingConfig;
import com.monolithic.ecommerce.models.Book;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(config = MappingConfig.class)
public interface BookMapper {
    Book toEntity(BookDTO bookDTO, Integer quantity);
    BookDTO toDTO(Book book);
    Book toEntity(BookRegistrationDTO bookRegistrationDTO);
}


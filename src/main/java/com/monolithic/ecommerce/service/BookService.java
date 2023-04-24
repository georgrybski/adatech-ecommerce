package com.monolithic.ecommerce.service;


import com.monolithic.ecommerce.dtos.book.BookDTO;
import com.monolithic.ecommerce.dtos.book.BookRegistrationDTO;
import com.monolithic.ecommerce.exceptions.BookNotFoundException;
import com.monolithic.ecommerce.mapper.BookMapper;
import com.monolithic.ecommerce.models.Book;
import com.monolithic.ecommerce.models.Transaction;
import com.monolithic.ecommerce.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private BookRepository bookRepository;
    private BookMapper bookMapper;

    public Book createBook(BookRegistrationDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        return bookRepository.save(book);
    }

    public List<BookDTO> fetchAllBooks() {
        return bookRepository.findAll()
                             .stream()
                             .map(bookMapper::toDTO)
                             .toList();
    }

    public BookDTO fetchBookById(Long id) {
        Book book = bookRepository.findById(id)
                             .orElseThrow(() -> new RuntimeException("No book with id %s found".formatted(id)));
        return bookMapper.toDTO(book);
    }

    public Book updateBook(BookDTO bookDTO) {
        fetchBookById(bookDTO.getBookId());
        Book book = bookMapper.toEntity(bookDTO, bookDTO.getQuantity());
        return bookRepository.save(book);
    }

    public void deleteBookById(Long id) {
        fetchBookById(id);
        bookRepository.deleteById(id);
    }

    public BookRegistrationDTO getBookRegistrationExample() {
        return BookRegistrationDTO.builder()
                .title("Example title")
                .author("Example author")
                .isbn("Example isbn")
                .publisher("Example publisher")
                .language("Example language")
                .description("Example description")
                .publicationDate("Example publication date")
                .numberOfPages(100)
                .category("Example category")
                .price(BigDecimal.valueOf(100))
                .genre("Example genre")
                .quantity(10)
                .build();
    }

    public Book findByIdOrThrow(Long bookId) {
        return bookRepository.findById(bookId)
                             .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    public void updateStock(Transaction transaction) {
        transaction.getTransactionItems().forEach(transactionItem -> {
            Book book = findByIdOrThrow(transactionItem.getBook().getBookId());
            book.setQuantity(book.getQuantity() - transactionItem.getQuantity());
            bookRepository.save(book);
        });
    }
}


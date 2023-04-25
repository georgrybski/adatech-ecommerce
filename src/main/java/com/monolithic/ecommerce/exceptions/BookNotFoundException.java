package com.monolithic.ecommerce.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BookNotFoundException extends RuntimeException {

    private static final String MESSAGE = "No book with id %s found";
    private final Long id;
    private final LocalDateTime timestamp;
    public BookNotFoundException(Long id) {
        super(MESSAGE.formatted(id));
        this.id = id;
        timestamp = LocalDateTime.now();
    }

}

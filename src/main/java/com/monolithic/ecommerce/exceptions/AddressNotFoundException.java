package com.monolithic.ecommerce.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AddressNotFoundException extends RuntimeException {

    private static final String MESSAGE = "No address with ID %s found";
    private final Long id;
    private final LocalDateTime timestamp;

    public AddressNotFoundException(Long id) {
        super(MESSAGE.formatted(id));
        this.id = id;
        timestamp = LocalDateTime.now();
    }
}

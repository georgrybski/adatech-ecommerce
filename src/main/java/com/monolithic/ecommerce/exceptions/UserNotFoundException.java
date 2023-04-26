package com.monolithic.ecommerce.exceptions;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserNotFoundException extends RuntimeException {
    private static final String ID_MESSAGE = "No user with ID %s found";
    private static final String EMAIL_MESSAGE = "No user with email %s found";
    private final Long id;
    private final String email;
    private final LocalDateTime timestamp;

    public UserNotFoundException(Long id) {
        super(ID_MESSAGE.formatted(id));
        this.id = id;
        email = null;
        timestamp = LocalDateTime.now();
    }

    public UserNotFoundException(String email) {
        super(EMAIL_MESSAGE.formatted(email));
        this.email = email;
        id = null;
        timestamp = LocalDateTime.now();
    }
}

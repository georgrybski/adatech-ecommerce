package com.monolithic.ecommerce.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class InsufficientFundsException extends RuntimeException{
    private static final String MESSAGE = "Account number %s has insufficient funds (Attempted to make purchase worth %s with only %s available)";
    private final LocalDateTime timestamp;

    public InsufficientFundsException(Long accountNumber, BigDecimal purchaseAmount, BigDecimal availableFunds) {
        super(MESSAGE.formatted(accountNumber, purchaseAmount, availableFunds));
        timestamp = LocalDateTime.now();
    }

}

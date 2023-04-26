package com.monolithic.ecommerce.exceptions;

public class InsufficientStockException extends RuntimeException {

    private static final String MESSAGE = "Requested more books than available, bookId: %s, requestedQuantity: %s, availableQuantity: %s";
    public InsufficientStockException(Long bookId, Integer requestedQuantity, Integer availableQuantity) {
        super(MESSAGE.formatted(bookId, requestedQuantity, availableQuantity));
    }
}

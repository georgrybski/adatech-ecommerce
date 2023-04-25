package com.monolithic.ecommerce.exceptions;

import com.monolithic.ecommerce.models.Transaction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class InvalidTransactionException extends RuntimeException {

    private static final String MESSAGE = "Invalid transaction, cause: ";
    private final boolean totalPriceValid;
    private final boolean unitPriceAndDiscountCorrect;
    private final boolean sufficientBalance;
    private final LocalDateTime timestamp;

    public InvalidTransactionException(boolean isTotalPriceValid, boolean isUnitPriceAndDiscountCorrect, boolean isBalanceEnough) {
        super(createMessage(isTotalPriceValid, isUnitPriceAndDiscountCorrect, isBalanceEnough));
        totalPriceValid = isTotalPriceValid;
        unitPriceAndDiscountCorrect = isUnitPriceAndDiscountCorrect;
        sufficientBalance = isBalanceEnough;
        timestamp = LocalDateTime.now();
    }

    private static String createMessage(boolean isTotalPriceValid, boolean isUnitPriceAndDiscountCorrect, boolean isBalanceEnough) {
        return MESSAGE +
                (isTotalPriceValid? "" : "[Invalid total price]") +
                (isUnitPriceAndDiscountCorrect? "" : "[Incorrect unit price and/or discount]") +
                (isBalanceEnough? "" : "[Insufficient funds]");
    }

}

package com.monolithic.ecommerce.dtos.transaction.creation;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionItemCreationDTO {
    private Long bookId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private BigDecimal totalPrice;
}

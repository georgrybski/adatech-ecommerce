package com.monolithic.ecommerce.dtos.transaction.creation;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class TransactionCreationDTO {
    private Long userId;
    private Long addressId;
    private BigDecimal totalPrice;
    private String status;
    private String paymentMethod;
    private String paymentStatus;
    private Long paymentId;
    private List<TransactionItemCreationDTO> transactionItems;
}

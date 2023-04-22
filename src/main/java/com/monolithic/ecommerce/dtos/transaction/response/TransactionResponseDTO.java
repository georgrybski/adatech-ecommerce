package com.monolithic.ecommerce.dtos.transaction.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.monolithic.ecommerce.dtos.address.AddressDTO;
import com.monolithic.ecommerce.dtos.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionResponseDTO {
    private Long transactionId;
    private UserDTO user;
    private AddressDTO address;
    private BigDecimal totalPrice;
    private String status;
    private String paymentMethod;
    private String paymentStatus;
    private List<TransactionItemResponseDTO> transactionItems;
}



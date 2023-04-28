package com.monolithic.ecommerce.controllers;


import com.monolithic.ecommerce.dtos.transaction.creation.TransactionCreationDTO;
import com.monolithic.ecommerce.dtos.transaction.response.TransactionResponseDTO;
import com.monolithic.ecommerce.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@AllArgsConstructor
public class TransactionController {
    private TransactionService transactionService;

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.fetchTransactionById(id));
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.fetchAllTransactions());
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionCreationDTO transactionCreationDTO) {
        transactionService.createTransaction(transactionCreationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(
            value = "/example",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TransactionCreationDTO> getExampleTransaction() {
        return ResponseEntity.ok(transactionService.getExampleTransactionCreationDTO());
    }
}

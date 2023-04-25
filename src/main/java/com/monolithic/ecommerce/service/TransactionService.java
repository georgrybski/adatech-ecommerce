package com.monolithic.ecommerce.service;


import com.monolithic.ecommerce.dtos.transaction.creation.TransactionCreationDTO;
import com.monolithic.ecommerce.dtos.transaction.creation.TransactionItemCreationDTO;
import com.monolithic.ecommerce.dtos.transaction.response.TransactionResponseDTO;
import com.monolithic.ecommerce.exceptions.InsufficientStockException;
import com.monolithic.ecommerce.exceptions.InvalidTransactionException;
import com.monolithic.ecommerce.mapper.TransactionMapper;
import com.monolithic.ecommerce.models.*;
import com.monolithic.ecommerce.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {
    private TransactionRepository transactionRepository;
    private TransactionMapper transactionMapper;
    private UserService userService;
    private AddressService addressService;
    private BookService bookService;

    public TransactionResponseDTO fetchTransactionById(Long id) {
        return transactionMapper.toTransactionResponseDTO(transactionRepository.findById(id).orElseThrow());
    }

    @Transactional
    public Transaction createTransaction(TransactionCreationDTO transactionCreationDTO) {

        User user = userService.findByIdOrThrow(transactionCreationDTO.getUserId());
        Address address = addressService.findByIdOrThrow(transactionCreationDTO.getAddressId());

        List<TransactionItem> transactionItems = new ArrayList<>();

        transactionCreationDTO.getTransactionItems().forEach(
                transactionItemCreationDTO -> transactionItems.add(
                        transactionMapper.toTransactionItem(
                                transactionItemCreationDTO,
                                bookService.findByIdOrThrow(transactionItemCreationDTO.getBookId())
                        )
                )
        );

        Transaction transaction = transactionMapper.toTransaction(transactionCreationDTO, user, address, transactionItems);
        checkTransaction(transaction);
        userService.withdrawBalance(user, transaction.getTotalPrice());
        bookService.updateStock(transaction);
        return transactionRepository.save(transaction);
    }

    public Transaction updatePaymentStatus(Long id, String status) {
        Transaction transaction = findByIdOrThrow(id);
        transaction.setPaymentStatus(status);
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransactionStatus(Long id, String status) {
        Transaction transaction = findByIdOrThrow(id);
        transaction.setStatus(status);
        return transactionRepository.save(transaction);
    }

    private Transaction findByIdOrThrow(Long id) {
        return transactionRepository.findById(id).orElseThrow();
    }

    private boolean checkTransaction(Transaction transaction) {

        BigDecimal totalPrice = transaction.getTransactionItems().stream()
                .map(TransactionItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDiscount = transaction.getTransactionItems().stream()
                .map(TransactionItem::getDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalUnitPrice = transaction.getTransactionItems().stream()
                .map((transactionItem) -> transactionItem.getUnitPrice().multiply(BigDecimal.valueOf(transactionItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        transaction.getTransactionItems().forEach((transactionItem -> {
            if (transactionItem.getBook().getQuantity() < transactionItem.getQuantity())
                throw new InsufficientStockException(transactionItem.getBook().getBookId(), transactionItem.getBook().getQuantity(), transactionItem.getQuantity());
        }));

        boolean isTotalPriceValid = totalPrice.compareTo(transaction.getTotalPrice()) == 0;
        boolean isUnitPriceAndDiscountCorrect = totalUnitPrice.subtract(totalDiscount).compareTo(transaction.getTotalPrice()) == 0;
        boolean isBalanceEnough = transaction.getUser().getBalance().compareTo(transaction.getTotalPrice()) >= 0;

        if (!isTotalPriceValid || !isUnitPriceAndDiscountCorrect || !isBalanceEnough) {
            throw new InvalidTransactionException(isTotalPriceValid, isUnitPriceAndDiscountCorrect, isBalanceEnough);
        }
        return true;
    }

    public List<TransactionResponseDTO> fetchAllTransactions() {
        return transactionRepository.findAll().stream()
                                              .map(transactionMapper::toTransactionResponseDTO)
                                              .toList();
    }

    public TransactionCreationDTO getExampleTransactionCreationDTO() {
        TransactionCreationDTO transactionCreationDTO = new TransactionCreationDTO();
        List<TransactionItemCreationDTO> transactionItemCreationDTOS = new ArrayList<>();
        TransactionItemCreationDTO transactionItemCreationDTO = new TransactionItemCreationDTO();
        transactionItemCreationDTO.setBookId(1L);
        transactionItemCreationDTO.setQuantity(2);
        transactionItemCreationDTO.setUnitPrice(BigDecimal.valueOf(100.0));
        transactionItemCreationDTO.setTotalPrice(BigDecimal.valueOf(200.0));
        transactionItemCreationDTO.setDiscount(BigDecimal.valueOf(0.0));
        transactionItemCreationDTOS.add(transactionItemCreationDTO);

        transactionItemCreationDTO = new TransactionItemCreationDTO();
        transactionItemCreationDTO.setBookId(2L);
        transactionItemCreationDTO.setQuantity(1);
        transactionItemCreationDTO.setUnitPrice(BigDecimal.valueOf(50.0));

        transactionItemCreationDTO.setTotalPrice(BigDecimal.valueOf(50.0));
        transactionItemCreationDTO.setDiscount(BigDecimal.valueOf(0.0));
        transactionItemCreationDTOS.add(transactionItemCreationDTO);

        transactionCreationDTO.setTransactionItems(transactionItemCreationDTOS);
        transactionCreationDTO.setUserId(1L);
        transactionCreationDTO.setAddressId(1L);
        transactionCreationDTO.setPaymentMethod("CASH");
        transactionCreationDTO.setPaymentStatus("PAID");
        transactionCreationDTO.setStatus("COMPLETED");
        transactionCreationDTO.setTotalPrice(BigDecimal.valueOf(250.0));
        return transactionCreationDTO;
    }
}

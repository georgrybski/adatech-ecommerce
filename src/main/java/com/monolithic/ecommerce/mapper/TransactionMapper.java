package com.monolithic.ecommerce.mapper;



import com.monolithic.ecommerce.dtos.transaction.creation.TransactionCreationDTO;
import com.monolithic.ecommerce.dtos.transaction.creation.TransactionItemCreationDTO;
import com.monolithic.ecommerce.dtos.transaction.response.TransactionItemResponseDTO;
import com.monolithic.ecommerce.dtos.transaction.response.TransactionResponseDTO;
import com.monolithic.ecommerce.mapper.config.MappingConfig;
import com.monolithic.ecommerce.models.*;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(config = MappingConfig.class)
public abstract class TransactionMapper {
    private UserMapper userMapper;
    private BookMapper bookMapper;
    private AddressMapper addressMapper;

    public Transaction toTransaction(TransactionCreationDTO transactionCreationDTO,
                                     User user,
                                     Address address, List<TransactionItem> transactionItems) {

        Transaction transaction = toTransaction(transactionCreationDTO, user, address);
        transaction.setTransactionItems(transactionItems);
        return transaction;
    }

    public abstract TransactionItem toTransactionItem(TransactionItemCreationDTO transactionItemDTO, Book book);

    public abstract TransactionResponseDTO toTransactionResponseDTO(Transaction transaction);

    public abstract TransactionItemResponseDTO toTransactionItemResponseDTO(TransactionItem transactionItem);

    protected abstract Transaction toTransaction(TransactionCreationDTO transactionCreationDTO,
                                                 User user,
                                                 Address address);
}
package com.monolithic.ecommerce.mapper;


import com.monolithic.ecommerce.dtos.user.UserDTO;
import com.monolithic.ecommerce.dtos.user.UserRegistrationDTO;
import com.monolithic.ecommerce.dtos.user.UserUpdateDTO;
import com.monolithic.ecommerce.models.User;
import lombok.AllArgsConstructor;
import com.monolithic.ecommerce.mapper.config.MappingConfig;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Mapper(config = MappingConfig.class)
public abstract class UserMapper {

    AddressMapper addressMapper;

    public abstract UserDTO toDTO(User user);
    public abstract User toEntity(UserDTO userDTO);
    public abstract User toEntity(UserUpdateDTO userUpdateDTO);

    public User toEntity(UserRegistrationDTO userRegistrationDTO, BigDecimal balance) {
        User user = toEntity(userRegistrationDTO);
        user.setBalance(balance);
        return user;
    }
    protected abstract User toEntity(UserRegistrationDTO userRegistrationDTO);
}

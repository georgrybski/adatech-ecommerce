package com.monolithic.ecommerce.dtos.user;


import com.monolithic.ecommerce.dtos.address.AddressRegistrationDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class UserUpdateDTO {
    private String name;

    private String email;

    private String password;
    private BigDecimal balance;
    private List<AddressRegistrationDTO> addresses;
}

package com.monolithic.ecommerce.dtos.user;


import com.monolithic.ecommerce.dtos.address.AddressDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private BigDecimal balance;
    private List<AddressDTO> addresses;
}

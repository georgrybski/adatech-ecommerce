package com.monolithic.ecommerce.dtos.address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private Long addressId;
    private String fullName;
    private String phoneNumber;
    private String street;
    private String number;
    private String city;
    private String country;
    private String zipCodeOrPostalCodeOrCEP;
}

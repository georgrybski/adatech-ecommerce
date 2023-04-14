package com.monolithic.ecommerce.dtos.address;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRegistrationDTO {
    private String fullName;
    private String phoneNumber;
    private String street;
    private String number;
    private String city;
    private String state;
    private String country;
    private String zipCodeOrPostalCodeOrCEP;
}

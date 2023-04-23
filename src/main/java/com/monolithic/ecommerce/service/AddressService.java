package com.monolithic.ecommerce.service;


import com.monolithic.ecommerce.dtos.address.AddressDTO;
import com.monolithic.ecommerce.dtos.address.AddressRegistrationDTO;
import com.monolithic.ecommerce.exceptions.AddressNotFoundException;
import com.monolithic.ecommerce.models.Address;
import com.monolithic.ecommerce.repository.AddressRepository;
import lombok.AllArgsConstructor;
import com.monolithic.ecommerce.mapper.AddressMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {
    private AddressRepository addressRepository;
    private AddressMapper addressMapper;

    public void deleteAddressById(Long id) {
        addressRepository.deleteById(id);
    }

    public AddressDTO fetchAddressById(Long id) {
        return addressMapper.toDTO(fetchByIdOrThrow(id));
    }

    public Address createAddress(AddressRegistrationDTO addressRegistrationDTO) {
        return addressRepository.save(addressMapper.toEntity(addressRegistrationDTO));
    }

    public Address updateAddress(Long id, AddressRegistrationDTO addressRegistrationDTO) {
        Address address = addressMapper.toEntity(addressRegistrationDTO);
        address.setAddressId(id);
        return addressRepository.save(address);
    }

    private Address fetchByIdOrThrow(Long id) {
        return addressRepository.findById(id).orElseThrow();
    }

    public Address findByIdOrThrow(Long addressId) {
        return addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));
    }
}

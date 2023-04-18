package com.monolithic.ecommerce.mapper;

import com.monolithic.ecommerce.dtos.address.AddressDTO;
import com.monolithic.ecommerce.dtos.address.AddressRegistrationDTO;
import com.monolithic.ecommerce.mapper.config.MappingConfig;
import com.monolithic.ecommerce.models.Address;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(config = MappingConfig.class)
public interface AddressMapper {

    Address toEntity(AddressDTO addressDTO);

    AddressDTO toDTO(Address address);

    Address toEntity(AddressRegistrationDTO addressRegistrationDTO);
}

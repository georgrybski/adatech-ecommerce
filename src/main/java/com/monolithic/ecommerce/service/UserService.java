package com.monolithic.ecommerce.service;

import com.monolithic.ecommerce.dtos.address.AddressRegistrationDTO;
import com.monolithic.ecommerce.dtos.user.UserDTO;
import com.monolithic.ecommerce.dtos.user.UserRegistrationDTO;
import com.monolithic.ecommerce.dtos.user.UserUpdateDTO;
import com.monolithic.ecommerce.exceptions.UserNotFoundException;
import com.monolithic.ecommerce.models.User;
import com.monolithic.ecommerce.repository.UserRepository;
import com.monolithic.ecommerce.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final PasswordService passwordService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordService = passwordService;
    }

    public void createUser(UserRegistrationDTO user) {
        User userEntity = userMapper.toEntity(user, user.getBalance());
        passwordService.hashUserPassword(userEntity);
        userRepository.save(userEntity);
    }

    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO).toList();
    }

    public UserDTO findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return userMapper.toDTO(user.get());
    }

    public UserDTO findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException(email);
        }
        return userMapper.toDTO(user.get());
    }

    public void updateUser(Long id, UserUpdateDTO userDTO) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException(id);
        }

        User user = userMapper.toEntity(userDTO);
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserRegistrationDTO getUserRegistrationDTOExample() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setName("Full Name");
        userRegistrationDTO.setEmail("email@example.com");
        userRegistrationDTO.setBalance(BigDecimal.valueOf(1000));
        userRegistrationDTO.setPassword("VeryStrongPassword123!");

        AddressRegistrationDTO addressRegistrationDTO = new AddressRegistrationDTO();
        addressRegistrationDTO.setFullName("Name Of The Person Living Here");
        addressRegistrationDTO.setPhoneNumber("phoneNumber");
        addressRegistrationDTO.setStreet("Name Of the Street");
        addressRegistrationDTO.setNumber("Number");
        addressRegistrationDTO.setCity("City");
        addressRegistrationDTO.setState("State");
        addressRegistrationDTO.setCountry("Country");
        addressRegistrationDTO.setZipCodeOrPostalCodeOrCEP("zipCodeOrPostalCodeOrCEP");

        userRegistrationDTO.getAddresses().add(addressRegistrationDTO);

        addressRegistrationDTO = new AddressRegistrationDTO();
        addressRegistrationDTO.setFullName("fullName2");
        addressRegistrationDTO.setPhoneNumber("phoneNumber2");
        addressRegistrationDTO.setStreet("street2");
        addressRegistrationDTO.setNumber("number2");
        addressRegistrationDTO.setCity("city2");
        addressRegistrationDTO.setState("state2");
        addressRegistrationDTO.setCountry("country2");
        addressRegistrationDTO.setZipCodeOrPostalCodeOrCEP("zipCodeOrPostalCodeOrCEP2");

        userRegistrationDTO.getAddresses().add(addressRegistrationDTO);

        return userRegistrationDTO;
    }

    public User findByIdOrThrow(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public User withdrawBalance(User user, BigDecimal totalPrice) {
        user.setBalance(user.getBalance().subtract(totalPrice));
        userRepository.save(user);
        return user;
    }
}

package com.monolithic.ecommerce.service;

import com.monolithic.ecommerce.models.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    private static final String PEPPER = "hVmYp3s6v9y$B&E)H@McQfTjWnZr4t7w!z%C*F-JaNdRgUkXp2s5v8x/A?D(G+Kb";

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void hashUserPassword(User user) {
        user.setPassword(hashPassword(user.getPassword()));
    }

    private String hashPassword(String password) {
        return bCryptPasswordEncoder.encode(password + PEPPER);
    }

    protected boolean isPasswordValid(String password, String hashedPassword) {
        return bCryptPasswordEncoder.matches(password + PEPPER, hashedPassword);
    }
}

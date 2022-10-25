package com.dzvonik.cashflow2.service.impl;

import com.dzvonik.cashflow2.model.User;
import com.dzvonik.cashflow2.repository.UserRepository;
import com.dzvonik.cashflow2.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // TODO: test
    @Override
    public User getCurrentUser() {
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Long userId = auth.getId();
        Long userId = 1L;
        User currentUser = userRepository.findById(userId).get();
        return currentUser;
    }

}

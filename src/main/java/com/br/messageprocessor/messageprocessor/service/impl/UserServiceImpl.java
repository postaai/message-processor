package com.br.messageprocessor.messageprocessor.service.impl;

import com.br.messageprocessor.messageprocessor.entity.UserEntity;
import com.br.messageprocessor.messageprocessor.repository.UserEntityRepository;
import com.br.messageprocessor.messageprocessor.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserEntityRepository repository;
    @Override
    public UserEntity save(UserEntity userEntity) {

        return repository.save(userEntity);
    }

    @Override
    public UserEntity findByUserId(String userId) {
        return repository.findByUserId(userId).orElse(null);
    }
}

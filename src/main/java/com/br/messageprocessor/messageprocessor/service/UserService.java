package com.br.messageprocessor.messageprocessor.service;

import com.br.messageprocessor.messageprocessor.entity.UserEntity;

import java.util.Optional;

public interface UserService {

    UserEntity save(UserEntity userEntity);

    UserEntity findByUserId(String userId);
}

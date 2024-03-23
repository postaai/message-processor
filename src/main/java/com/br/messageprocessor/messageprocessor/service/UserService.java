package com.br.messageprocessor.messageprocessor.service;

import com.br.messageprocessor.messageprocessor.entity.UserEntity;

public interface UserService {

    UserEntity save(UserEntity userEntity);

    UserEntity findByUserId(String userId);
}

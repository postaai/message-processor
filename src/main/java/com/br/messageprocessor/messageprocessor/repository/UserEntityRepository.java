package com.br.messageprocessor.messageprocessor.repository;

import com.br.messageprocessor.messageprocessor.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends MongoRepository<UserEntity, String> {

   Optional<UserEntity> findByUserId(String userId);
}

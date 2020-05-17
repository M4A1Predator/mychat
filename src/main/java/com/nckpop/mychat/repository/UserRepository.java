package com.nckpop.mychat.repository;

import com.nckpop.mychat.entity.CustomUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<CustomUser, String> {

    Optional<CustomUser> findByUsername(String username);

}

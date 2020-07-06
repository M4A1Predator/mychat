package com.nckpop.mychat.repository;

import com.nckpop.mychat.entity.CustomUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<CustomUser, String> {

    Optional<CustomUser> findByUsername(String username);

    @Query("{ '_id': { '$ne' : ObjectId('?0') } }")
    List<CustomUser> findAllExceptSelf(String id);

}

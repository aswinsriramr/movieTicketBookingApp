package com.movieticketbooking.userservice.respositories;

import com.movieticketbooking.userservice.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    public User findByLoginId(String loginId);
}

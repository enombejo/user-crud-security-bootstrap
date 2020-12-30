package com.example.user_crud_spring_boot.dao;

import com.example.user_crud_spring_boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query("delete User where id = :id")
    void deleteUserById(@Param("id") long id);

    @Query("FROM User WHERE email = :email")
    User findUserByEmail(@Param("email") String email);
}

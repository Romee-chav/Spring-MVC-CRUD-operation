package com.mvc_crud.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mvc_crud.Entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

}

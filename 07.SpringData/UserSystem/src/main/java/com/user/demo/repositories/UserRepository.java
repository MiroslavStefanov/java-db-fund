package com.user.demo.repositories;

import com.user.demo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByEmailEndingWith(String host);

    List<User> findAllByLastTimeLoggedInBefore(Date date);
}

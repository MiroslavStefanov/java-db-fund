package com.softuni.json.products_shop.repositories;

import com.softuni.json.products_shop.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User AS u WHERE u.soldProducts IS NOT EMPTY ORDER BY u.lastName, u.firstName")
    List<User> findAllWithSoldProdcuts();
}

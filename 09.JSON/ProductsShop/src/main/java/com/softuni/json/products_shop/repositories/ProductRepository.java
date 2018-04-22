package com.softuni.json.products_shop.repositories;

import com.softuni.json.products_shop.models.dto.view.Query1.ProductWithSellerViewModel;
import com.softuni.json.products_shop.models.dto.view.Query4.ProductWithNameAndPriceViewModel;
import com.softuni.json.products_shop.models.entities.Product;
import com.softuni.json.products_shop.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT new com.softuni.json.products_shop.models.dto.view.Query1.ProductWithSellerViewModel(p.name, p.price, TRIM(CONCAT(COALESCE(s.firstName, '') , ' ', s.lastName))) " +
            "FROM Product AS p " +
            "JOIN p.seller AS s " +
            "WHERE p.price BETWEEN :from AND :to " +
            "AND p.buyer IS NULL " +
            "ORDER BY p.price ASC")
    List<ProductWithSellerViewModel> findAllInRangeWithNoBuyer(@Param("from")BigDecimal from, @Param("to")BigDecimal to);

    /*@Query("SELECT new com.softuni.json.products_shop.models.dto.view.Query4.ProductWithNameAndPriceViewModel(p.name, p.price) " +
            "FROM Product AS p " +
            "WHERE p.seller = :seller")
    List<ProductWithNameAndPriceViewModel> findAllWithNameAndPriceBySeller(@Param("seller")User seller);*/
}

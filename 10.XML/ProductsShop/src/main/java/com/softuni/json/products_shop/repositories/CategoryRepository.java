package com.softuni.json.products_shop.repositories;

import com.softuni.json.products_shop.models.dto.view.Query3.CategoryWithProductCountViewModel;
import com.softuni.json.products_shop.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT new com.softuni.json.products_shop.models.dto.view.Query3.CategoryWithProductCountViewModel(c.name, COUNT(p), AVG(p.price), SUM(p.price)) " +
            "FROM Category AS c " +
            "LEFT JOIN c.products AS p " +
            "GROUP BY c.id " +
            "ORDER BY COUNT(p) DESC")
    List<CategoryWithProductCountViewModel> findAllWithProductCount();
}

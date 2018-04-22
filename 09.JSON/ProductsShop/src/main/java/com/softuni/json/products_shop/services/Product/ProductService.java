package com.softuni.json.products_shop.services.Product;

import com.softuni.json.products_shop.models.dto.binding.ProductCreateBindingModel;
import com.softuni.json.products_shop.models.dto.view.Query1.ProductWithSellerViewModel;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    void save(ProductCreateBindingModel[] productModels);

    List<ProductWithSellerViewModel> findAllProductsWithPriceInRangeWithNoBuyer(BigDecimal from, BigDecimal to);
}

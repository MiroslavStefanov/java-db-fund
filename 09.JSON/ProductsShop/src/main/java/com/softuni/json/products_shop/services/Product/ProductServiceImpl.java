package com.softuni.json.products_shop.services.Product;

import com.softuni.json.products_shop.models.dto.binding.ProductCreateBindingModel;
import com.softuni.json.products_shop.models.dto.view.Query1.ProductWithSellerViewModel;
import com.softuni.json.products_shop.models.entities.Product;
import com.softuni.json.products_shop.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(ProductCreateBindingModel[] productModels) {
        List<Product> products = new ArrayList<>();
        for (ProductCreateBindingModel userModel : productModels) {
            if(userModel.validate())
                products.add(this.mapper.map(userModel, Product.class));
        }

        this.productRepository.saveAll(products);
    }

    @Override
    public List<ProductWithSellerViewModel> findAllProductsWithPriceInRangeWithNoBuyer(BigDecimal from, BigDecimal to) {
        return this.productRepository.findAllInRangeWithNoBuyer(from, to);
    }
}

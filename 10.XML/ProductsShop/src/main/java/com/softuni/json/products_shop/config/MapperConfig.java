package com.softuni.json.products_shop.config;

import com.softuni.json.products_shop.models.dto.binding.ProductCreateBindingModel;
import com.softuni.json.products_shop.models.dto.view.Query2.SoldProductViewModel;
import com.softuni.json.products_shop.models.dto.view.Query2.UserWithSoldProductsViewModel;
import com.softuni.json.products_shop.models.dto.view.Query4.ProductWithNameAndPriceViewModel;
import com.softuni.json.products_shop.models.dto.view.Query4.SoldProductsInfoViewModel;
import com.softuni.json.products_shop.models.dto.view.Query4.UserWithAgeAndSoldProductViewModel;
import com.softuni.json.products_shop.models.entities.Category;
import com.softuni.json.products_shop.models.entities.Product;
import com.softuni.json.products_shop.models.entities.User;
import com.softuni.json.products_shop.repositories.CategoryRepository;
import com.softuni.json.products_shop.repositories.UserRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;
import java.util.*;

@Configuration
@Transactional
public class MapperConfig {

    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public MapperConfig(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = new ModelMapper();
        this.configure();
    }

    private void configure() {
        productCreateMapping();
        soldProductViewMapping();
        usersWithSoldProductsMapping();
    }

    private void productCreateMapping() {

        Converter<Set<Integer>, Set<Category>> fetchCat = ctx -> {
            List<Category> cat = this.categoryRepository.findAllById(ctx.getSource());
            Set<Category> cats = new HashSet<>();
            cats.addAll(cat);
            return cats;
        };

        Converter<Integer, User> fetchUser = ctx -> {
            if(ctx.getSource() == null)
                return null;
            Optional<User> user = this.userRepository.findById(ctx.getSource());
            return user.orElse(null);
        };

        this.mapper.addConverter(fetchCat);

        this.mapper.createTypeMap(ProductCreateBindingModel.class, Product.class).addMappings(m -> {
            m.using(fetchUser).map(ProductCreateBindingModel::getBuyer, Product::setBuyer);
            m.using(fetchUser).map(ProductCreateBindingModel::getSeller, Product::setSeller);
            m.using(fetchCat).map(ProductCreateBindingModel::getCategories, Product::setCategories);
        });
    }

    private void soldProductViewMapping() {

        this.mapper.createTypeMap(Product.class, SoldProductViewModel.class).addMappings(m -> {
            m.map(src -> src.getBuyer().getFirstName(), SoldProductViewModel::setBuyerFirstName);
            m.map(src -> src.getBuyer().getLastName(), SoldProductViewModel::setBuyerLastName);
        });

        Converter<Set<Product>, Set<SoldProductViewModel>> con = ctx -> {
            Set<SoldProductViewModel> set = new HashSet<>();
            ctx.getSource().forEach(x -> set.add(this.mapper.map(x, SoldProductViewModel.class)));
            return set;
        };

        this.mapper.createTypeMap(User.class, UserWithSoldProductsViewModel.class)
                .addMappings(m -> m.using(con).map(User::getSoldProducts, UserWithSoldProductsViewModel::setSoldProducts));
    }

    private void usersWithSoldProductsMapping() {
        Converter<Set<Product>, SoldProductsInfoViewModel> con = ctx -> {
            List<ProductWithNameAndPriceViewModel> p = new ArrayList<>();
            ctx.getSource().forEach(x -> p.add(this.mapper.map(x, ProductWithNameAndPriceViewModel.class)));
            return new SoldProductsInfoViewModel(p);
        };

        this.mapper.createTypeMap(User.class, UserWithAgeAndSoldProductViewModel.class)
                .addMappings(m -> m.using(con).map(User::getSoldProducts, UserWithAgeAndSoldProductViewModel::setSoldProducts));
    }

    @Bean
    public ModelMapper getMapper() {
        return mapper;
    }
}

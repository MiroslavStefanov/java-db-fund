package com.softuni.json.products_shop;

import com.google.gson.Gson;
import com.softuni.json.products_shop.models.dto.binding.CategoryBindingModel;
import com.softuni.json.products_shop.models.dto.binding.ProductCreateBindingModel;
import com.softuni.json.products_shop.models.dto.binding.UserCreateBindingModel;
import com.softuni.json.products_shop.models.dto.view.Query3.CategoryWithProductCountViewModel;
import com.softuni.json.products_shop.models.dto.view.Query1.ProductWithSellerViewModel;
import com.softuni.json.products_shop.models.dto.view.Query2.UserWithSoldProductsViewModel;
import com.softuni.json.products_shop.models.dto.view.Query4.UserWithAgeAndSoldProductViewModel;
import com.softuni.json.products_shop.models.dto.view.Query4.UsersInfoViewModel;
import com.softuni.json.products_shop.services.Category.CategoryService;
import com.softuni.json.products_shop.services.Product.ProductService;
import com.softuni.json.products_shop.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class CmdRunner implements CommandLineRunner {

    private final Gson gson;
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;

    private static final String USERS_JSON_PATH = "/inputJson/users.json";
    private static final String PRODUCTS_JSON_PATH = "/inputJson/products.json";
    private static final String CATEGORIES_JSON_PATH = "/inputJson/categories.json";
    private static final String OUTPUT_JSON_DIR = System.getProperty("user.dir") + "/src/main/resources/outputJson/";

    @Autowired
    public CmdRunner(Gson gson,
                     UserService userService,
                     ProductService productService,
                     CategoryService categoryService) {
        this.gson = gson;
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {

        //seedData();

        //exportAllProductsInRange();
        //exportAllUsersWithSoldProducts();
        //exportAllCategoriesWithProductCount();
        exportAllUsers();
    }


    private void exportAllProductsInRange() {
        List<ProductWithSellerViewModel> list = this.productService.findAllProductsWithPriceInRangeWithNoBuyer(BigDecimal.valueOf(500l), BigDecimal.valueOf(1000));
        String data = this.gson.toJson(list);
        this.writeToFile(OUTPUT_JSON_DIR + "products-in-range.json", data);
    }

    private void exportAllUsersWithSoldProducts() {
        List<UserWithSoldProductsViewModel> list = this.userService.findAllWithSoldProducts();
        String data = this.gson.toJson(list);
        this.writeToFile(OUTPUT_JSON_DIR + "users-sold-products.json", data);
    }

    private void exportAllCategoriesWithProductCount() {
        List<CategoryWithProductCountViewModel> list = this.categoryService.findAllWithProductCount();
        String data = this.gson.toJson(list);
        this.writeToFile(OUTPUT_JSON_DIR + "categories-by-products.json", data);
    }

    private void exportAllUsers() {
        List<UserWithAgeAndSoldProductViewModel> list = this.userService.findAllWhoSoldProducts();
        UsersInfoViewModel model = new UsersInfoViewModel(list);
        String data = this.gson.toJson(model);
        this.writeToFile(OUTPUT_JSON_DIR + "users-and-products.json", data);
    }


    private void seedData() {
        String userData = "", productData = "", categoryData ="";
        try {
             userData = StreamUtils.copyToString(loadFile(USERS_JSON_PATH), Charset.defaultCharset());
             productData = StreamUtils.copyToString(loadFile(PRODUCTS_JSON_PATH), Charset.defaultCharset());
             categoryData = StreamUtils.copyToString(loadFile(CATEGORIES_JSON_PATH), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        UserCreateBindingModel[] users = this.gson.fromJson(userData, UserCreateBindingModel[].class);
        ProductCreateBindingModel[] products = this.gson.fromJson(productData, ProductCreateBindingModel[].class);
        CategoryBindingModel[] categories = this.gson.fromJson(categoryData, CategoryBindingModel[].class);

        this.userService.save(users);
        this.categoryService.save(categories);

        Random random = new Random();
        int buyer, seller, countCategories, cat;
        for (ProductCreateBindingModel p : products) {

            buyer = random.nextInt(users.length + 7);
            if(buyer < users.length)
                p.setBuyer(buyer);

            do{
                seller = random.nextInt(users.length - 1) + 1;
            }while (seller == buyer);
            p.setSeller(seller);

            countCategories = random.nextInt(categories.length);
            for(int i = 0; i < countCategories; i++) {
                cat = random.nextInt(categories.length - 1) + 1;
                p.getCategories().add(cat);
            }
        }

        this.productService.save(products);
    }

    private InputStream loadFile(String path) {
        return this.getClass().getResourceAsStream(path);
    }

    private void writeToFile(String path, String src) {
        try {
            FileWriter writer = new FileWriter(new File(path));
            writer.write(src);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

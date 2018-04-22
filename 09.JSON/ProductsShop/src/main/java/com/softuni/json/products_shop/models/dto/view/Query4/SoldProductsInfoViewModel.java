package com.softuni.json.products_shop.models.dto.view.Query4;

import java.util.List;

public class SoldProductsInfoViewModel {

    private int count;
    private List<ProductWithNameAndPriceViewModel> products;

    public SoldProductsInfoViewModel(List<ProductWithNameAndPriceViewModel> products) {
        this.products = products;
        this.count = products.size();
    }

    public SoldProductsInfoViewModel() {
    }

    public int getCount() {
        return count;
    }

    public List<ProductWithNameAndPriceViewModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductWithNameAndPriceViewModel> products) {
        this.products = products;
    }
}

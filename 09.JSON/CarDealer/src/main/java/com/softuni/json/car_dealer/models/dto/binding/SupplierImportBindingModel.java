package com.softuni.json.car_dealer.models.dto.binding;

public class SupplierImportBindingModel {

    private String name;
    private Boolean isImporter;

    public SupplierImportBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getImporter() {
        return isImporter;
    }

    public void setImporter(Boolean importer) {
        isImporter = importer;
    }
}

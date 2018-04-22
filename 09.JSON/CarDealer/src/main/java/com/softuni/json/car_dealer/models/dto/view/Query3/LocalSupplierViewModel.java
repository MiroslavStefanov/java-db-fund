package com.softuni.json.car_dealer.models.dto.view.Query3;

import java.io.Serializable;

public class LocalSupplierViewModel implements Serializable {

    private Integer Id;
    private String Name;
    private Long partsCount;

    public LocalSupplierViewModel(Integer id, String name, Long partsCount) {
        Id = id;
        Name = name;
        this.partsCount = partsCount;
    }

    public LocalSupplierViewModel() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Long getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(Long partsCount) {
        this.partsCount = partsCount;
    }
}

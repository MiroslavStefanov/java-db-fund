package com.softuni.gamestore.model.dto.binding;

public class GameEditBindingModel {

    private Long id;
    private GameAddBindingModel model;

    public GameEditBindingModel() {
    }

    public GameEditBindingModel(Long id, GameAddBindingModel model) {
        this.id = id;
        this.model = model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameAddBindingModel getModel() {
        return model;
    }

    public void setModel(GameAddBindingModel model) {
        this.model = model;
    }

    public boolean validate() {
        return this.model.validate();
    }
}

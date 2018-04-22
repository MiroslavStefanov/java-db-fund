package com.softuni.gamestore.model.dto.view;

public class UserLoginViewModel {

    private Long id;
    private String fullName;

    public UserLoginViewModel() {
    }

    public UserLoginViewModel(Long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

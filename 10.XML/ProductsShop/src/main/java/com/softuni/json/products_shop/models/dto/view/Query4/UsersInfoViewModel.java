package com.softuni.json.products_shop.models.dto.view.Query4;

import java.util.List;

public class UsersInfoViewModel {

    private int usersCount;
    private List<UserWithAgeAndSoldProductViewModel> users;

    public UsersInfoViewModel(List<UserWithAgeAndSoldProductViewModel> users) {
        this.users = users;
        this.usersCount = users.size();
    }

    public int getUsersCount() {
        return usersCount;
    }

    public List<UserWithAgeAndSoldProductViewModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithAgeAndSoldProductViewModel> users) {
        this.users = users;
    }
}

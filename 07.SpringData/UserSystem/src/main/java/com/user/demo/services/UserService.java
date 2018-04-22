package com.user.demo.services;


import com.user.demo.model.entity.User;

import java.util.Date;
import java.util.List;

public interface UserService {

    List<User> getAllByEmailHost(String host);

    List<User> getAllByLastLoggedInBefore(Date date);

    void saveIntoDB(List<User> users);

    void saveIntoDB(User user);

    void deleteFromDB(List<User> users);
}

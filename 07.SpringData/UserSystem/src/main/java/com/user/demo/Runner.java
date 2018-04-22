package com.user.demo;

import com.user.demo.model.entity.Town;
import com.user.demo.model.entity.User;
import com.user.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Runner implements CommandLineRunner {

    private final UserService userService;
    private final Scanner scanner;

    @Autowired
    public Runner(UserService userService) {
        this.userService = userService;
        scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {

        //printUsersByHost();
        //removeInactiveUsers();

        //testing hibernate validator
        insertUsers();
    }

    private void printUsersByHost() {

        String host = this.scanner.nextLine();
        List<User> users = this.userService.getAllByEmailHost(host);
        for(User u : users)
            System.out.println(u.getUsername() + " " + u.getEmail());
    }

    private void removeInactiveUsers() {
        List<User> users = this.userService.getAllByLastLoggedInBefore(new Date());
        users.forEach(x -> {
            x.setDeleted(1);
        });
        System.out.printf("%d users have been deleted", users.size());
        this.userService.deleteFromDB(users);
    }

    private void insertUsers() {
        User valid = new User();
        valid.setUsername("pandicacao");
        valid.setEmail("email@gmail.com");
        valid.setPassword("Parola1234!@");

        User invalidEmail = new User();
        invalidEmail.setUsername("invalidEmai");
        invalidEmail.setEmail("abv@abv");
        invalidEmail.setPassword("Abcd@$99");

        User shortPassword = new User();
        shortPassword.setUsername("shortPassword");
        shortPassword.setEmail("ab_c@abv.bg");
        shortPassword.setPassword("A!b9");

        User invalidPassword = new User();
        invalidPassword.setUsername("invalidPassword");
        invalidPassword.setEmail("ab_cd@abv.bg.com");
        invalidPassword.setPassword("Abvcdeasfd");

        List<User> users = Arrays.asList(valid, invalidEmail, shortPassword, invalidPassword);
        this.userService.saveIntoDB(users);
    }
}

package com.user.demo.services;

import com.user.demo.model.entity.User;
import com.user.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Validator validator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    @Override
    public List<User> getAllByEmailHost(String host) {
        return this.userRepository.findAllByEmailEndingWith(host);
    }

    @Override
    public List<User> getAllByLastLoggedInBefore(Date date) {
        return this.userRepository.findAllByLastTimeLoggedInBefore(date);
    }

    @Override
    public void saveIntoDB(List<User> users) {

        List<User> validUsers = new ArrayList<>();
        for(User user : users) {
            Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
            if(constraintViolations.size() > 0) {
                System.out.printf("User %s has not been added.\nReaseons: ", user.getUsername());
                for(ConstraintViolation<User> v : constraintViolations) {
                    System.out.println(v.getMessage());
                }
            } else {
                validUsers.add(user);
            }
        }
        this.userRepository.saveAll(validUsers);
    }

    @Override
    public void saveIntoDB(User user) {

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        if(constraintViolations.size() > 0) {
            System.out.printf("User %s has not been added.\nReaseons: ", user.getUsername());
            for(ConstraintViolation<User> v : constraintViolations) {
                System.out.println(v.getMessage());
            }
        }
        this.userRepository.save(user);
    }

    @Override
    public void deleteFromDB(List<User> users) {
        this.userRepository.deleteAll(users);
    }
}

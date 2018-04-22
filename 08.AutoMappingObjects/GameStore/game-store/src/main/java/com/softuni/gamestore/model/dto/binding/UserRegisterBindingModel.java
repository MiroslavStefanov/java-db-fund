package com.softuni.gamestore.model.dto.binding;

import com.softuni.gamestore.validation.Email;
import com.softuni.gamestore.validation.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class UserRegisterBindingModel {

    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    public UserRegisterBindingModel() {
    }

    public UserRegisterBindingModel(String email, String password, String confirmPassword, String fullName) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
    }

    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Password(minLength = 6)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean validate() {

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(this);
        if(violations.size()>0) {
            for(ConstraintViolation<UserRegisterBindingModel> v : violations)
                System.out.println(v.getMessage());

            return false;
        }

        if(!this.confirmPassword.equals(this.password)) {
            System.out.println("Password doesn't match");
            return false;
        }

        return true;
    }
}

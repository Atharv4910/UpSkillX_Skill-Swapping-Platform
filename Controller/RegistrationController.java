package com.upskillx.Controller;


import com.upskillx.dao.UserDAO;
import com.upskillx.model.User;

public class RegistrationController {

    private UserDAO userDAO = new UserDAO();

    public void registerUser(User user) {
        userDAO.saveUser(user);
    }
    
}

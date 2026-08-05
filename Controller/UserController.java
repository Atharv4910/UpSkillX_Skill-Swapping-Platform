package com.upskillx.Controller;

import com.upskillx.dao.UserDAO;

public class UserController {

    private UserDAO userDAO = new UserDAO();

    public void sendRequest(String fromEmail, String toEmail) {
        userDAO.sendConnectionRequest(fromEmail, toEmail, null);
    }

    public void acceptRequest(String currentEmail, String fromEmail) {
        userDAO.updateRequestStatus(currentEmail, fromEmail, "accepted", null);
    }

    public void rejectRequest(String currentEmail, String fromEmail) {
        userDAO.updateRequestStatus(currentEmail, fromEmail, "rejected", null);
    }

}


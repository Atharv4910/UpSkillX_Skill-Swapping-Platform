package com.upskillx;

import com.upskillx.View.FeedBack;
import com.upskillx.View.landing_page;
import com.upskillx.dao.UserDAO;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        UserDAO.initFirebase();
        Application.launch(landing_page.class,args);
    }
}

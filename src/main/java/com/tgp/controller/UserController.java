package com.tgp.controller;

import com.tgp.db.Dbi;
import com.tgp.db.dao.UserDao;
import com.tgp.model.User;
import com.tgp.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class UserController {

    private static UserDao userDao = Dbi.getInstance().open(UserDao.class);

    public List<Pair<Integer, String>> getListOfNames() {
        List<User> users = userDao.getAllUsers();

        List<Pair<Integer, String>> names = new ArrayList<>();
        for (User user : users) {
            names.add(new Pair(user.getId(), user.getLastName() + ", " + user.getFirstName()));
        }

        return names;
    }

    public int insertUser(String firstName, String lastName) {
        return userDao.insertUser(firstName, lastName);
    }

    public User findUserByName(String firstName, String lastName) {
        return userDao.findUserByName(firstName, lastName);
    }

    public int deleteUser(User user) {
        return userDao.deleteUser(user.getId());
    }

    public User findUserById(int id) {
        return userDao.findUserById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}

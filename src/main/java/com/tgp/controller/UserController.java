package com.tgp.controller;

import com.tgp.db.Dbi;
import com.tgp.db.dao.UserDao;
import com.tgp.model.User;
import com.tgp.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class UserController {

   public List<Pair<Integer, String>> getListOfNames() {
      UserDao dao = Dbi.getInstance().open(UserDao.class);
      List<User> users = dao.getAllUsers();

      List<Pair<Integer, String>> names = new ArrayList<>();
      for (User user : users) {
         names.add(new Pair(user.getId(),user.getLastName() + ", " + user.getFirstName()));
      }

      return names;
   }
}

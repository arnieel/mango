package com.tgp.db.dao.mapper;

import com.tgp.model.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<User> {

   @Override
   public User map(int index, ResultSet r, StatementContext ctx) throws SQLException {
      int id = r.getInt("ID");
      String firstName = r.getString("FIRSTNAME");
      String lastName = r.getString("LASTNAME");

      User user = new User(firstName, lastName);
      user.setId(id);
      return user;
   }
}

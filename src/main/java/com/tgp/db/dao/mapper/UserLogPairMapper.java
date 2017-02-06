package com.tgp.db.dao.mapper;

import com.tgp.model.Log;
import com.tgp.model.User;
import com.tgp.util.Pair;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLogPairMapper implements ResultSetMapper<Pair<User, Log>> {

   @Override
   public Pair<User, Log> map(int index, ResultSet r, StatementContext ctx) throws SQLException {
      User user = new UserMapper().map(index, r, ctx);
      Log log = new LogMapper().map(index, r, ctx);
      return new Pair<>(user, log);
   }
}

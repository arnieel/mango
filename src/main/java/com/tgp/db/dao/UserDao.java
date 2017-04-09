package com.tgp.db.dao;

import com.tgp.db.dao.mapper.UserMapper;
import com.tgp.model.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

public interface UserDao extends BaseDao {

   @SqlUpdate("INSERT INTO user (first_name, last_name) VALUES (:first_name, :last_name)")
   int insertUser(@Bind("first_name") String first_name, @Bind("last_name") String last_name);

   @SqlUpdate("UPDATE user SET first_name=:first_name, last_name=:last_name WHERE id=:id")
   int updateUser(@Bind("id") int id, @Bind("first_name") String first_name, @Bind("last_name") String last_name);

   @SqlUpdate("UPDATE user SET deleted=1 WHERE id=:id")
   int deleteUser(@Bind("id") int id);

   @SqlQuery("SELECT id, first_name, last_name FROM user WHERE first_name=:first_name AND last_name=:last_name AND deleted=0")
   @RegisterMapper(UserMapper.class)
   User findUserByName(@Bind("first_name") String first_name, @Bind("last_name") String last_name);

   @SqlQuery("SELECT id, first_name, last_name FROM user WHERE id=:id")
   @RegisterMapper(UserMapper.class)
   User findUserById(@Bind("id") int userId);

   @SqlQuery("SELECT id, first_name, last_name FROM user WHERE deleted=0")
   @RegisterMapper(UserMapper.class)
   List<User> getAllUsers();



}

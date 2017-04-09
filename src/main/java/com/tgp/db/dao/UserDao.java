package com.tgp.db.dao;

import com.tgp.db.dao.mapper.UserMapper;
import com.tgp.model.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

public interface UserDao extends BaseDao {

   @SqlUpdate("INSERT INTO user (firstname, lastname) VALUES (:firstName, :lastName)")
   int insertUser(@Bind("firstName") String firstName, @Bind("lastName") String lastName);

   @SqlUpdate("UPDATE user SET firstname=:firstName, lastname=:lastName WHERE id=:id")
   int updateUser(@Bind("id") int id, @Bind("firstName") String firstName, @Bind("lastName") String lastName);

   @SqlUpdate("UPDATE user SET deleted=1 WHERE id=:id")
   int deleteUser(@Bind("id") int id);

   @SqlQuery("SELECT id, firstname, lastname FROM user WHERE firstname=:firstname AND lastname=:lastname AND deleted=0")
   @RegisterMapper(UserMapper.class)
   User findUserByName(@Bind("firstname") String firstname, @Bind("lastname") String lastname);

   @SqlQuery("SELECT id, firstname, lastname FROM user WHERE id=:id")
   @RegisterMapper(UserMapper.class)
   User findUserById(@Bind("id") int userId);

   @SqlQuery("SELECT id, firstname, lastname FROM user WHERE deleted=0")
   @RegisterMapper(UserMapper.class)
   List<User> getAllUsers();



}

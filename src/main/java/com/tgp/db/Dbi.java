package com.tgp.db;

import org.h2.jdbcx.JdbcConnectionPool;
import org.skife.jdbi.v2.DBI;

public class Dbi {

   private static DBI dbi;

   private Dbi() {
   }

   public static DBI getInstance() {
      if (dbi == null) {
         JdbcConnectionPool ds = JdbcConnectionPool.create(HOST, USER, PASSWORD);
         dbi = new DBI(ds);
      }
      return dbi;
   }

   private static final String HOST = "jdbc:h2:tcp://localhost/~/tgp;SCHEMA=tgp";
   private static final String USER = "tgpadmin";
   private static final String PASSWORD = "root1234";
}

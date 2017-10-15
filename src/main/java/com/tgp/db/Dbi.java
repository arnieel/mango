package com.tgp.db;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.tgp.util.PropertiesUtil;
import org.skife.jdbi.v2.DBI;

public class Dbi {

    private static DBI dbi;

    private Dbi() {
    }

    public static DBI getInstance() {
        if (dbi == null) {
            MysqlDataSource ds = new MysqlDataSource();
            ds.setDatabaseName(PropertiesUtil.getValue("mysql.db.name"));
            ds.setServerName(PropertiesUtil.getValue("mysql.db.host"));
            ds.setUser(PropertiesUtil.getValue("mysql.db.user"));
            ds.setPassword(PropertiesUtil.getValue("mysql.db.password"));
            ds.setPort(Integer.valueOf(PropertiesUtil.getValue("mysql.db.port")));
            dbi = new DBI(ds);
        }
        return dbi;
    }

}

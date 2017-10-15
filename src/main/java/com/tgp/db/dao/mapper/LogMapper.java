package com.tgp.db.dao.mapper;

import com.tgp.model.Log;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LogMapper implements ResultSetMapper<Log> {

    @Override
    public Log map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        Log log = new Log();
        log.setId(r.getInt("id"));
        log.setImagePath(r.getString("image_path"));
        log.setIn(r.getBoolean("is_in"));
        log.setTime(r.getTimestamp("time"));
        log.setUserId(r.getInt("user_id"));
        return log;
    }
}

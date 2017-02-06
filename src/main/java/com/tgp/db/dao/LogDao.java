package com.tgp.db.dao;

import com.tgp.db.dao.mapper.LogMapper;
import com.tgp.model.Log;
import com.tgp.model.User;
import com.tgp.util.Pair;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

public interface LogDao extends BaseDao {

   @SqlUpdate("INSERT INTO log (user_id, time, in, image_path) VALUES (:userId, NOW(), :in, :imagePath)")
   void logUser(@Bind("userId") int userId, @Bind("in") boolean isIn, @Bind("imagePath") String imagePath);

   @SqlQuery("SELECT id, user_id, time, in, image_path FROM log WHERE time::date = CURDATE() AND in = 1 AND user_id=:id")
   @RegisterMapper(LogMapper.class)
   List<Log> findInLogForToday(@Bind("id") int userId);

   @SqlQuery("SELECT id, user_id, time, in, image_path FROM log WHERE time::date = CURDATE() AND in = 0 AND user_id=:id")
   @RegisterMapper(LogMapper.class)
   List<Log> findOutLogForToday(@Bind("id") int userId);

   @SqlUpdate("INSERT INTO admin_log (type, image_path, time) VALUES (:type, :imagePath, NOW())")
   void logAdminActivity(@Bind("type") String type, @Bind("imagePath") String imagePath);

   @SqlQuery("SELECT * FROM log l JOIN user u ON l.user_id=u.id WHERE l.time::date >= :startDate AND l.time::date <= :endDate AND u.id=:id AND u.deleted=0")
   @RegisterMapper(LogMapper.class)
   List<Log> getLogsByDateRangeAndByUser(@Bind("startDate") String startDate, @Bind("endDate") String endDate, @Bind("id") int userId);
}

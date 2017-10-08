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

   @SqlUpdate("INSERT INTO log (user_id, time, is_in, image_path) VALUES (:userId, NOW(), :is_in, :imagePath)")
   void logUser(@Bind("userId") int userId, @Bind("is_in") boolean isIn, @Bind("imagePath") String imagePath);

   @SqlQuery("SELECT id, user_id, time, is_in, image_path FROM log WHERE DATE(time) = CURDATE() AND is_in = 1 AND user_id=:id")
   @RegisterMapper(LogMapper.class)
   List<Log> findInLogForToday(@Bind("id") int userId);

   @SqlQuery("SELECT id, user_id, time, is_in, image_path FROM log WHERE DATE(time) = CURDATE() AND is_in = 0 AND user_id=:id")
   @RegisterMapper(LogMapper.class)
   List<Log> findOutLogForToday(@Bind("id") int userId);

   @SqlQuery("SELECT * FROM log l JOIN user u ON l.user_id=u.id WHERE DATE(l.time) >= :startDate AND DATE(l.time) <= :endDate AND u.id=:id AND u.deleted=0")
   @RegisterMapper(LogMapper.class)
   List<Log> getLogsByDateRangeAndByUser(@Bind("startDate") String startDate, @Bind("endDate") String endDate, @Bind("id") int userId);
}

package com.tgp.controller;

import com.tgp.db.Dbi;
import com.tgp.db.dao.LogDao;
import com.tgp.model.Log;

import java.util.List;

public class LogController {

    private static LogDao logDao = Dbi.getInstance().open(LogDao.class);

    public List<Log> findInLogForToday(int userId) {
        return logDao.findInLogForToday(userId);
    }

    public void logUser(int userId, boolean isIn, String imagePath) {
        logDao.logUser(userId, isIn, imagePath);
    }

    public List<Log> findOutLogForToday(int userId) {
        return logDao.findOutLogForToday(userId);
    }

    public List<Log> getLogsByDateRangeAndByUser(String startDate, String endDate, int userId) {
        return logDao.getLogsByDateRangeAndByUser(startDate, endDate, userId);
    }
}

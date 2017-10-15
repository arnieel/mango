package com.tgp.view.viewmodel;

public class LogViewModel {
    private String logType;
    private String time;
    private String imagePath;

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getLogType() {
        return logType;
    }

    public String getTime() {
        return time;
    }
}

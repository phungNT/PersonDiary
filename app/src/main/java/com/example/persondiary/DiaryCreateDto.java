package com.example.persondiary;

import net.sourceforge.jtds.jdbc.DateTime;

import java.io.Serializable;

public class DiaryCreateDto implements Serializable {
    private String dateTime;
    private String title;
    private String content;
    private String status;
    private String description;
    private int userId;
    public DiaryCreateDto() {
    }

    public DiaryCreateDto(String dateTime, String title, String content, String status, String description, int userId) {
        this.dateTime = dateTime;
        this.title = title;
        this.content = content;
        this.status = status;
        this.description = description;
        this.userId = userId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

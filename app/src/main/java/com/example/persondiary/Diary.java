package com.example.persondiary;

import java.io.Serializable;

public class Diary implements Serializable {
    public int diaryId ;
    public String title;
    public String content ;
    public String status ;
    public String dateTime ;
    public String description ;
    public  int userId ;

    public Diary() {
    }

    public Diary(int diaryId, String title, String content, String status, String dateTime, String description, int userId) {
        this.diaryId = diaryId;
        this.title = title;
        this.content = content;
        this.status = status;
        this.dateTime = dateTime;
        this.description = description;
        this.userId = userId;
    }

    public int getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(int diaryId) {
        this.diaryId = diaryId;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

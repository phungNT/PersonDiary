package com.example.persondiary;

import java.io.Serializable;

public class DiaryReadDto implements Serializable {
    private String dateTime;
    private String title;

    public DiaryReadDto(String dateTime, String title){
        this.dateTime = dateTime;
        this.title = title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getTitle() {
        return title;
    }
}

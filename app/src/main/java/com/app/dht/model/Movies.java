package com.app.dht.model;

public class Movies {
    private  String title;
    private  String year;
    private  String poster;
    private  String type;


    public Movies() {

    }
    public Movies(String title, String year, String poster, String type) {
        this.title = title;
        this.year = year;
        this.poster = poster;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}

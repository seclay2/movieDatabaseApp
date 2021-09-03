package io.github.seclay2.api.entity;

import java.sql.Date;

public class Watch {
    private Integer id;
    private Date date;
    private Movie movie;
    private Platform platform;
    private Float cost;

    public Watch(Integer id, Date date, Movie movie, Platform platform, float cost) {
        this.id = id;
        this.date = date;
        this.movie = movie;
        this.platform = platform;
        this.cost = cost;
    }

    public Watch(Date date, Movie movie, Platform platform, float cost) {
        this.date = date;
        this.movie = movie;
        this.platform = platform;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Watch{" +
                "id=" + id +
                ", date=" + date +
                ", movie=" + movie +
                ", platform=" + platform +
                ", cost=" + cost +
                '}';
    }
}

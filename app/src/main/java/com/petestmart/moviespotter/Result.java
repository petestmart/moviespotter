package com.petestmart.moviespotter;

public class Result {
    public int id;
    public String overview;
    public String poster_path;
    public String release_date;
    public String title;
    public double vote_average;

    public Result(
            int id,
            String overview,
            String poster_path,
            String release_date,
            String title,
            Double vote_average) {
        this.id = id;
        this.overview = overview;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.title = title;
        this.vote_average = vote_average;
    }
}

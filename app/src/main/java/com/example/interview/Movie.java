package com.example.interview;

public class Movie {
    private String title, year;
    public Movie(){

    }
    public Movie(String title,   String year){

        this.title = title;
    //    this.genre = genre;
        this.year = year;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getYear(){
        return this.year;
    }

    public void setYear(String year){
        this.year = year;
    }
/*
    public String getGenre(){
        return  this.genre;
    }

    public void setGenre(){
        this.genre = genre;
    }
*/
}

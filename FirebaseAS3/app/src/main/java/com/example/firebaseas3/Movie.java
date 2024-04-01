package com.example.firebaseas3;

public class Movie {

    String documentId;
    String movietitle;
    String moviestudio;
    String movierating;
    String moviethumb;


    public Movie(){

    }
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getMovietitle() {
        return movietitle;
    }

    public void setMovietitle(String movietitle) {
        this.movietitle = movietitle;
    }

    public String getMoviestudio() {
        return moviestudio;
    }

    public void setMoviestudio(String moviestudio) {
        this.moviestudio = moviestudio;
    }

    public String getMovierating() {
        return movierating;
    }

    public void setMovierating(String movierating) {
        this.movierating = movierating;
    }

    public String getMoviethumb() {
        return moviethumb;
    }

    public void setMoviethumb(String moviethumb) {
        this.moviethumb = moviethumb;
    }


    public Movie(String documentId, String movietitle, String moviestudio, String moviethumb, String movierating) {
        this.documentId = documentId;
        this.movietitle = movietitle;
        this.moviestudio = moviestudio;
        this.moviethumb = moviethumb;
        this.movierating = movierating;
    }




}

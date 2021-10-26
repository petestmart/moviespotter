package com.petestmart.moviespotter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PopularMovies {

    @NotNull
    public final List<? extends Result> results;

    public PopularMovies(List<? extends Result> results) {
        this.results = results;
    }
}
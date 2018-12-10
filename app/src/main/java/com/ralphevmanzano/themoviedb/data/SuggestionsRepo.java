package com.ralphevmanzano.themoviedb.data;

import com.ralphevmanzano.themoviedb.data.local.dao.SuggestionsDao;
import com.ralphevmanzano.themoviedb.data.remote.MovieDBService;

import javax.inject.Inject;

public class SuggestionsRepo {

    private MovieDBService movieDBService;
    private SuggestionsDao suggestionsDao;

    @Inject
    public SuggestionsRepo(MovieDBService movieDBService, SuggestionsDao suggestionsDao) {
        this.movieDBService = movieDBService;
        this.suggestionsDao = suggestionsDao;
    }
}

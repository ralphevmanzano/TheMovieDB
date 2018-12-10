package com.ralphevmanzano.themoviedb.data;

import com.ralphevmanzano.themoviedb.data.local.dao.ReviewsDao;
import com.ralphevmanzano.themoviedb.data.remote.MovieDBService;

import javax.inject.Inject;

public class ReviewsRepo {

    private MovieDBService movieDBService;
    private ReviewsDao reviewsDao;

    @Inject
    public ReviewsRepo(MovieDBService movieDBService, ReviewsDao reviewsDao) {
        this.movieDBService = movieDBService;
        this.reviewsDao = reviewsDao;
    }
}

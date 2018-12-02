package com.ralphevmanzano.themoviedb.data;

import android.text.TextUtils;

import com.ralphevmanzano.themoviedb.data.local.dao.VideosDao;
import com.ralphevmanzano.themoviedb.data.models.VideosResponse;
import com.ralphevmanzano.themoviedb.data.local.entity.Video;
import com.ralphevmanzano.themoviedb.data.remote.MovieDBService;
import com.ralphevmanzano.themoviedb.utils.Constants;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import timber.log.Timber;

public class VideosRepo {

    private MovieDBService movieDBService;
    private VideosDao videosDao;

    @Inject
    VideosRepo(MovieDBService movieDBService, VideosDao videosDao) {
        this.movieDBService = movieDBService;
        this.videosDao = videosDao;
    }

    public Flowable<Resource<Video>> loadMovieVideos(long id) {
        return new NetworkBoundResource<Video, VideosResponse>() {
            @Override
            protected boolean shouldFetch() {
                Timber.d("should fetch");
                return true;
            }

            @Override
            protected void saveCallResult(VideosResponse item) {
                Timber.d("save call result %s", item.getId());
                if (item != null) {
                    if (item.getVideos().size() > 0) {
                        Video vid = item.getVideos().get(0);
                        vid.setMovieId(id);
                        videosDao.insert(vid);
                    }
                }
            }

            @NonNull
            @Override
            protected Flowable<Video> loadFromDb() {
                Timber.d("load from db");
                return videosDao.getMovieVideo(id);
            }

            @NonNull
            @Override
            protected Flowable<VideosResponse> createCall() {
                return movieDBService.getMovieVideos(id, Constants.API_KEY);
            }
        }.toFlowable();
    }

    private void handleVideo(Video video) {
        videosDao.insert(video);
    }
}

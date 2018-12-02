package com.ralphevmanzano.themoviedb.data;

import com.ralphevmanzano.themoviedb.data.remote.ApiResponse;
import com.ralphevmanzano.themoviedb.data.models.MovieResponse;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public abstract class NetworkBoundResource<ResultType, RequestType> {

    private Flowable<Resource<ResultType>> result;

    @MainThread
    NetworkBoundResource() {
        Flowable<Resource<ResultType>> source;

        if (shouldFetch()) {
            source = createCall()
                    .subscribeOn(Schedulers.io())
                    .doOnNext(this::saveCallResult)
                    .flatMap(apiResponse -> loadFromDb().map(Resource::success))
                    .doOnError(t -> onFetchFailed())
                    .onErrorResumeNext(t -> {
                        return loadFromDb().map(data -> Resource.error(t.getMessage(), data));
                    })
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            source = loadFromDb()
                    .subscribeOn(Schedulers.io())
                    .map(Resource::success);
        }

//        result = Flowable.concat(loadFromDb().map(Resource::loading)
//                                             .take(1)
//                                             .startWith(Resource.loading(null)), source)
//                         .skip(1);
//        result = source.startWith(loadFromDb().map(Resource::loading)
//                                             .take(1));
        result = source;
    }

    protected void onFetchFailed() {
    }

    protected Flowable<Resource<ResultType>> toFlowable() {
        return result;
    }

    @MainThread
    protected abstract boolean shouldFetch();

    @WorkerThread
    protected abstract void saveCallResult(RequestType item);

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }

    @NonNull
    @MainThread
    protected abstract Flowable<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Flowable<RequestType> createCall();
}

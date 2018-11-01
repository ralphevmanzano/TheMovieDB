package com.ralphevmanzano.themoviedb.data;

import com.ralphevmanzano.themoviedb.data.remote.ApiResponse;

import org.jetbrains.annotations.NotNull;

import javax.xml.transform.Result;

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
            Timber.d("should fetch true");
            source = createCall()
                    .subscribeOn(Schedulers.io())
                    .doOnNext(apiResponse -> {
                        Timber.d("Responseee %s", apiResponse.toString());
//                        Timber.d("doOnNext %s", processResponse(apiResponse).toString());
                        saveCallResult(apiResponse);
                    })
                    .flatMap(apiResponse -> loadFromDb().map(Resource::success))
                    .doOnError(t -> {
                        Timber.e("onFetchFailed");
                        onFetchFailed();
                    })
                    .onErrorResumeNext(t -> {
                        return loadFromDb().map(data -> Resource.error(t.getMessage(), data));
                    })
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            source = loadFromDb()
                    .subscribeOn(Schedulers.io())
                    .map(Resource::success);
        }
        result = Flowable.concat(loadFromDb().map(Resource::loading).take(1), source);
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
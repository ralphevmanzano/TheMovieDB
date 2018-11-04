package com.ralphevmanzano.themoviedb.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel<T> extends ViewModel {
    private final MutableLiveData<T> selected = new MutableLiveData<T>();

    public void select(T item) {
        selected.setValue(item);
    }

    public LiveData<T> getSelected() {
        return selected;
    }
}

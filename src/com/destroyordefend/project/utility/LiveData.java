package com.destroyordefend.project.utility;

import java.util.Observable;

public class LiveData<T> extends Observable {
    T data;

    public LiveData(T data) {
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
        setChanged();
        notifyObservers();
    }
}

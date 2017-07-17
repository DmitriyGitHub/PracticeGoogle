package ru.valerevich.dmitriy.practicegoogle.DataBinding.model;

import android.support.annotation.NonNull;

import java.util.UUID;

public class User {
    private UUID mUserId;
    private String mName;
    private int mInn;

    public User(){
        mUserId = UUID.randomUUID();
    }

    public User(@NonNull String name, @NonNull int inn) {
        mUserId = UUID.randomUUID();
        mName = name;
        mInn = inn;
    }

    public @NonNull String getName() {
        return mName;
    }

    public @NonNull void setName(String name) {
        this.mName = String.valueOf(name);
    }

    public @NonNull int getInn() {
        return mInn;
    }

    public @NonNull void setInn(int inn) {
        this.mInn = mInn;
    }

    public @NonNull UUID getUserId() {
        return mUserId;
    }
}

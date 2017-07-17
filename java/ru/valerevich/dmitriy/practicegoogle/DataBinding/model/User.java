package ru.valerevich.dmitriy.practicegoogle.databinding.model;

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
        mName = name;
    }

    public @NonNull String getInn() {
        return String.valueOf(mInn);
    }

    public @NonNull void setInn(int inn) {
        mInn = mInn;
    }

    public @NonNull UUID getUserId() {
        return mUserId;
    }
}

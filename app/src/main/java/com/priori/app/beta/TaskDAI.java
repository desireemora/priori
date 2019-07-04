package com.priori.app.beta;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface TaskDAI {

    @Insert
    public void addTask(TaskDB task);
}

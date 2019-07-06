package com.priori.app.beta;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {TaskDB.class},version = 1,exportSchema = false)
public abstract class PrioriDB extends RoomDatabase {

    public abstract TaskDAI myTaskDai();


}

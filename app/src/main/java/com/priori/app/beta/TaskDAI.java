package com.priori.app.beta;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TaskDAI {

    @Insert
    public void addTask(TaskDB task);

    @Query("select * from TaskDB")
    List<TaskDB> getTasks();

    @Query("select * from TaskDB order by DueDate ASC , DueTime ASC")
    List<TaskDB> getTasksSortedByDate();

    @Query("select * from TaskDB where UserID = :user_id order by DueDate ASC , DueTime ASC")
    List<TaskDB> getTasksSortedUser(String user_id);


    @Query("delete from TaskDB where TaskName = :tskname")
    public void deleteTaskByName(String tskname);

    @Delete
    public void deleteTask(TaskDB task);


}

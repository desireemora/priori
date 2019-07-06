package com.priori.app.beta;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TaskDB {

    @PrimaryKey(autoGenerate = true)
    private int TaskID;

    private String TaskName;

    private String DueDate;

    private String DueTime;

    private String DoneDate;

    private String UserID;



    //Get Methods
    public int getTaskID() {
        return TaskID;
    }

    public String getTaskName() {
        return TaskName;
    }

    public String getDueDate() {
        return DueDate;
    }

    public String getDueTime() {
        return DueTime;
    }

    public String getDoneDate() {
        return DoneDate;
    }

    public String getUserID() {
        return UserID;
    }



    //Set Methods
    public void setTaskID(int taskID) {
        TaskID = taskID;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public void setDueTime(String dueTime) {
        DueTime = dueTime;
    }

    public void setDoneDate(String doneDate) {
        DoneDate = doneDate;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }


}

package com.example.finalflight.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalflight.Logs;

import java.util.List;

@Dao
public interface LogsDao {
    @Insert
    void insert(Logs... logs);

    @Update
    void update(Logs... logs);

    @Delete
    void delete(Logs logs);

    @Query("SELECT * FROM "+ MainDatabase.LOG_TABLE+" ORDER BY mTime DESC")
    List<Logs> getLogs();
}

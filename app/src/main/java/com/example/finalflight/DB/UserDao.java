package com.example.finalflight.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalflight.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM "+MainDatabase.USER_TABLE+"")
    List<User> getUsers();
    @Query("SELECT * FROM "+MainDatabase.USER_TABLE+" WHERE mUsername = :username")
    User getUser(String username);
    @Query("DELETE FROM "+MainDatabase.USER_TABLE)
    void nukeTable();
}

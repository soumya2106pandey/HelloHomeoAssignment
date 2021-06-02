package com.example.hellohomeo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.hellohomeo.database.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Delete()
    void deleteAll(List<User> allUsers);

    @Query("SELECT * FROM User_table ORDER BY User_Id ASC")
    LiveData<List<User>> getUser();
}

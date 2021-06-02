package com.example.hellohomeo.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {

    private UserDao userDao;
    private LiveData<List<User>> users;

    public UserRepository(Application application){
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        userDao = db.getUserDao();
        users = userDao.getUser();
    }

    public LiveData<List<User>> getAllUsers(){
        return users;
    }

    void insert(User user){
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insert(user);
        });
    }

    void delete(){
        UserRoomDatabase.databaseWriteExecutor.execute(() ->{
            userDao.deleteAll(users.getValue());
        });

    }

}

package com.example.hellohomeo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserRoomDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();
    public static volatile UserRoomDatabase INSTANCE;
    public static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static UserRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (UserRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),UserRoomDatabase.class, "user_database").build();
                }
            }
        }
        return INSTANCE;
    }

}

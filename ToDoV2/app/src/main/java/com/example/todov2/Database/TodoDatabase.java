package com.example.todov2.Database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Database(entities = TodoEntry.class, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class TodoDatabase extends RoomDatabase {

    private static Object LOCK = new Object();
    private static final String databaseName = "todoDatabase";
    private static TodoDatabase sInstance;

    public static TodoDatabase getInstance(Context context){

        if(sInstance == null){
            synchronized (LOCK){

                Log.d("database", "Creating new TodoDatabase instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(), TodoDatabase.class, databaseName).build();

            }
        }

        Log.d("database", "getting TodoDatabase");
        return sInstance;
    }

    public abstract TodoDao todoDao();

}

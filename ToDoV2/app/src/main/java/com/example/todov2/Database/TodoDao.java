package com.example.todov2.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Query("SELECT * FROM todo ORDER BY priority")
    LiveData<List<TodoEntry>> loadAllTodo();

    @Insert
    void  insertTodo(TodoEntry todoEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTodo(TodoEntry todoEntry);

    @Delete
    void deleteTodo(TodoEntry todoEntry);

    @Query("SELECT * FROM todo WHERE id = :id")
    LiveData<TodoEntry> loadTaskById(int id);
}

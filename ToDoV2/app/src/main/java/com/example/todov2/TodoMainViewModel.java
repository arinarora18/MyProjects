package com.example.todov2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todov2.Database.TodoDatabase;
import com.example.todov2.Database.TodoEntry;

import java.util.List;

public class TodoMainViewModel extends AndroidViewModel {

    private LiveData<List<TodoEntry>> tasks;

    public TodoMainViewModel(Application application) {
        super(application);
        TodoDatabase database = TodoDatabase.getInstance(this.getApplication());
        tasks = database.todoDao().loadAllTodo();
    }

    public LiveData<List<TodoEntry>> getTasks() {
        return tasks;
    }
}

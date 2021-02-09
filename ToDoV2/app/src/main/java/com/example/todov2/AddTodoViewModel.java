package com.example.todov2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todov2.Database.TodoDatabase;
import com.example.todov2.Database.TodoEntry;

import java.util.List;

public class AddTodoViewModel extends ViewModel {

    private LiveData<TodoEntry> todo;

    public
    AddTodoViewModel(TodoDatabase database, int id){
        todo = database.todoDao().loadTaskById(id);
    }

    public LiveData<TodoEntry> getTodoEntries(){return todo;}
}

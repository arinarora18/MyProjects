package com.example.todov2;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todov2.Database.TodoDatabase;

public class AddTodoViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final TodoDatabase mDatabase;
    private final int id;

    public AddTodoViewModelFactory(TodoDatabase mDatabase, int id) {
        this.mDatabase = mDatabase;
        this.id = id;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new AddTodoViewModel(mDatabase, id);
    }
}

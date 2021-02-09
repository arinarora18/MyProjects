package com.example.todov2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.todov2.Database.TodoDatabase;
import com.example.todov2.Database.TodoEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity implements TodoAdapter.ItemClickListener{

    private RecyclerView mRecyclerView;
    private TodoAdapter mTodoAdapter;
    private TodoDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recylerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTodoAdapter = new TodoAdapter(this, this);
        mRecyclerView.setAdapter(mTodoAdapter);

        DividerItemDecoration mDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(mDecorator);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<TodoEntry> list = mTodoAdapter.getmTodoEntry();
                        mDatabase.todoDao().deleteTodo(list.get(position));
                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);

        FloatingActionButton addTodoButton = findViewById(R.id.fab);
        addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTodoIntent = new Intent(MainActivity.this, AddTodoActivity.class);
                startActivity(addTodoIntent);
            }
        });

        mDatabase = TodoDatabase.getInstance(getApplicationContext());
        setupViewModel();
    }

    public void setupViewModel(){
        TodoMainViewModel viewModel = ViewModelProviders.of(this).get(TodoMainViewModel.class);
        viewModel.getTasks().observe(this, new Observer<List<TodoEntry>>() {
            @Override
            public void onChanged(List<TodoEntry> todoEntries) {
                mTodoAdapter.setTodo(todoEntries);
            }
        });
    }
    @Override
    public void onItemClickListener(int id) {
        Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
        intent.putExtra(AddTodoActivity.EXTRA_TASK_ID, id);
        startActivity(intent);
    }
}
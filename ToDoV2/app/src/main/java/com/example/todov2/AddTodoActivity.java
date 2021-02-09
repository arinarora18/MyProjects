package com.example.todov2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.todov2.Database.TodoDatabase;
import com.example.todov2.Database.TodoEntry;

import java.util.Date;

public class AddTodoActivity extends AppCompatActivity {

    public static final String INSTANCE_TASK_ID = "instanceTaskId";

    public static final String EXTRA_TASK_ID = "extraTaskId";

    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;

    private static final int DEFAULT_TASK_ID = -1;

    EditText mEditText;
    RadioGroup mRadioGroup;
    Button mButton;

    private int mTaskId = DEFAULT_TASK_ID;

    private TodoDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_todo_activity);

        placeViews();

        mDatabase = TodoDatabase.getInstance(getApplicationContext());

        if(savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)){
            mTaskId = savedInstanceState.getInt(EXTRA_TASK_ID, DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_TASK_ID)){
            mButton.setText("UPDATE");
            if(mTaskId == DEFAULT_TASK_ID){
                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);

                AddTodoViewModelFactory addTodoViewModelFactory = new AddTodoViewModelFactory(mDatabase, mTaskId);
                final AddTodoViewModel viewModel = ViewModelProviders.of(this, addTodoViewModelFactory).get(AddTodoViewModel.class);

                viewModel.getTodoEntries().observe(this, new Observer<TodoEntry>() {
                    @Override
                    public void onChanged(TodoEntry todoEntry) {
                        viewModel.getTodoEntries().removeObserver(this);
                        populateUI(todoEntry);
                    }
                });
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId);
        super.onSaveInstanceState(outState);
    }

    private void placeViews(){
        mEditText = findViewById(R.id.todoEditText);
        mRadioGroup = findViewById(R.id.radioGroup);
        mButton = findViewById(R.id.addTodoButton);
        
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddTodoButtonClicked();
            }
        });
    }

    public void populateUI(TodoEntry todo){
        if(todo == null){
            return;
        }
        mEditText.setText(todo.getDescription());
        setPriorityInView(todo.getPriority());
    }

    public void onAddTodoButtonClicked() {
        String description = mEditText.getText().toString();
        int priority = getPriorityFromView();
        Date date = new Date();
        final TodoEntry todo = new TodoEntry(description, priority, date);

        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                if(mTaskId == DEFAULT_TASK_ID){
                    mDatabase.todoDao().insertTodo(todo);
                }
                else{
                    todo.setId(mTaskId);
                    mDatabase.todoDao().updateTodo(todo);
                }
                finish();
            }
        });
    }

    public int getPriorityFromView(){
        int priority = 1;
        int checkedRadioButtonId = ((RadioGroup)findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
        switch (checkedRadioButtonId){
            case R.id.radioButton1 : priority = PRIORITY_LOW;
                                    break;
            case R.id.radioButton2 : priority = PRIORITY_MEDIUM;
                                    break;
            case R.id.radioButton3 : priority = PRIORITY_HIGH;
                                    break;
        }
        return priority;
    }

    public void setPriorityInView(int priority){
        switch (priority){
            case PRIORITY_LOW : ((RadioGroup)findViewById(R.id.radioGroup)).check(R.id.radioButton1);
                                break;
            case PRIORITY_MEDIUM : ((RadioGroup)findViewById(R.id.radioGroup)).check(R.id.radioButton2);
                                break;
            case PRIORITY_HIGH : ((RadioGroup)findViewById(R.id.radioGroup)).check(R.id.radioButton3);
                                break;
        }
    }
}

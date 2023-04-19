package com.example.todotimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton add;
    private RecyclerView rv_todos;
    private List<TodoModal> modalList;
    TodoAdapter adapter;
    LocalDatabaseHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.add);
        rv_todos = findViewById(R.id.rv_todos);
        helper = new LocalDatabaseHelper(MainActivity.this);

        add.setOnClickListener(vi -> {
            startActivity(new Intent(MainActivity.this, AddTodo.class));
        });

        rv_todos.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        //todo fetch from local database
        modalList = new ArrayList<>();
        synchronized (this) {
            modalList = helper.getAllTodos();
            Toast.makeText(this, "Size is : "+modalList.size(), Toast.LENGTH_SHORT).show();
        }
        adapter = new TodoAdapter(modalList, MainActivity.this, helper);
        rv_todos.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        synchronized (this) {
            modalList = helper.getAllTodos();
            adapter = new TodoAdapter(modalList, MainActivity.this,helper);
            Toast.makeText(this, "Size = " +modalList.size(), Toast.LENGTH_SHORT).show();
        }
    }

    //use interfaces and helpers to update data
}
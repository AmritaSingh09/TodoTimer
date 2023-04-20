package com.example.todotimer;

import androidx.annotation.Nullable;
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
            startActivityForResult(new Intent(MainActivity.this, AddTodo.class),1000);
        });

        rv_todos.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        modalList = new ArrayList<>();
        synchronized (this) {
            modalList = helper.getAllTodos();
        }
        adapter = new TodoAdapter(modalList, MainActivity.this, helper);
        rv_todos.setAdapter(adapter);

    }

    /*@Override
    protected void onResume() {
        synchronized (this) {
            modalList = helper.getAllTodos();
            adapter = new TodoAdapter(modalList, MainActivity.this,helper);
        }
        super.onResume();
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            modalList.add(modalList.size(), (TodoModal) data.getSerializableExtra("data"));
            if (adapter != null){
                adapter.notifyItemInserted(modalList.size()-1);
            }else {
                modalList = helper.getAllTodos();
                adapter = new TodoAdapter(modalList, MainActivity.this,helper);
            }
        }

    }
}
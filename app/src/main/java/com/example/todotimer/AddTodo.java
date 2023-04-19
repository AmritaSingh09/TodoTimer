package com.example.todotimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddTodo extends AppCompatActivity {

    private EditText et_title, et_desc, et_min, et_sec;
    private ImageView min_up, min_down, sec_up, sec_down;
    private AppCompatButton upload;
    private int time_in_sec = 0;
    LocalDatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        initViews();

        toolbar.setNavigationOnClickListener(v ->{
            finish();
        });

        upload.setOnClickListener(vi -> {
            synchronized (this) {
                if (et_title.getText().toString().isEmpty()) {
                    et_title.setError("Required!");
                    et_title.requestFocus();
                } else if (et_min.getText().toString().isEmpty() && et_sec.getText().toString().isEmpty()) {
                    et_min.setError("Required duration!");
                    et_min.requestFocus();
                } else {
                    try {
                        time_in_sec = Integer.parseInt(et_min.getText().toString()) * 60 + Integer.parseInt(et_sec.getText().toString());
                    } catch (Exception e) {
                        Toast.makeText(this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    if (time_in_sec == 0 || time_in_sec < 1 || time_in_sec > 600){
                        Toast.makeText(this, "Duration of at-least 1 second to up-to 10 minutes are required!", Toast.LENGTH_SHORT).show();
                    }else {
                        TodoModal modal = new TodoModal(et_title.getText().toString(), et_desc.getText().toString()+"", time_in_sec, time_in_sec, String.valueOf(System.currentTimeMillis()));
                        uploadToStorage(modal);
                    }

                }
            }
        });

        /*et_min.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    int m = Integer.parseInt(et_min.getText().toString());
                    if (m >= 10){
                        Toast.makeText(AddTodo.this, "Timer can't be more than 10 minutes!", Toast.LENGTH_SHORT).show();
                    }
                }catch (NumberFormatException e){
                    et_min.setText("");
                    Toast.makeText(AddTodo.this, "Only valid numbers are allowed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_sec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                try {
                    //if (charSequence.charAt()) this position is not integer then set second edittext to 00
                    int mi = Integer.parseInt(et_min.getText().toString());
                    int se = Integer.parseInt(et_sec.getText().toString());
                    if (se <= 0){
                        if (mi <= 0) Toast.makeText(AddTodo.this, "Timer should contain at-least 1 second and at-most 10 minutes!", Toast.LENGTH_SHORT).show();
                    }else {
                        if (mi >= 10) {
                            Toast.makeText(AddTodo.this, "Timer should not contain more than 10 minutes!", Toast.LENGTH_SHORT).show();
                            et_sec.setText("00");
                        }
                        if (se > 59) Toast.makeText(AddTodo.this, "Kindly change minutes!", Toast.LENGTH_SHORT).show();
                    }
                }catch (NumberFormatException e){
                    et_sec.setText("");
                    Toast.makeText(AddTodo.this, "Only valid numbers are allowed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
        //todo remove text change listener after uploading to db

        min_up.setOnClickListener(min -> {
            try {
                int m = Integer.parseInt(et_min.getText().toString());
                if (m >= 10){
                    Toast.makeText(this, "Timer can't be more than 10 minutes!", Toast.LENGTH_SHORT).show();
                }else {
                    et_min.setText(String.valueOf(m+1));
                }
            }catch (NumberFormatException e){
                et_min.setText("");
                Toast.makeText(this, "Only valid numbers are allowed!", Toast.LENGTH_SHORT).show();
            }
        });

        sec_up.setOnClickListener(min -> {
            try {
                int m = Integer.parseInt(et_min.getText().toString());
                int s = Integer.parseInt(et_sec.getText().toString());
                if (s >= 59){
                    Toast.makeText(this, "Timer can't be more than 59 seconds!", Toast.LENGTH_SHORT).show();
                }else {
                    if (m >= 10) Toast.makeText(this, "Timer can't be more than 10 minutes!", Toast.LENGTH_SHORT).show();
                    else et_sec.setText(String.valueOf(s+1));
                }
            }catch (NumberFormatException e){
                et_sec.setText("");
                Toast.makeText(this, "Only valid numbers are allowed!", Toast.LENGTH_SHORT).show();
            }
        });

        min_down.setOnClickListener(min -> {
            try {
                int m = Integer.parseInt(et_min.getText().toString());
                if (m <= 0){
                    Toast.makeText(this, "Minutes can't be negative kindly change the seconds!", Toast.LENGTH_SHORT).show();
                }else {
                    et_min.setText(String.valueOf(m-1));
                }
            }catch (NumberFormatException e){
                et_min.setText("");
                Toast.makeText(this, "Only valid numbers are allowed!", Toast.LENGTH_SHORT).show();
            }
        });

        sec_down.setOnClickListener(min -> {
            try {
                int mi = Integer.parseInt(et_min.getText().toString());
                int se = Integer.parseInt(et_sec.getText().toString());
                if (se <= 0){
                    if (mi <= 0) Toast.makeText(this, "Timer should contain at-least 1 second and at-most 10 minutes!", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Seconds can't be negative kindly change the seconds!", Toast.LENGTH_SHORT).show();
                }else {
                    et_sec.setText(String.valueOf(se-1));
                }
            }catch (NumberFormatException e){
                et_sec.setText("");
                Toast.makeText(this, "Only valid numbers are allowed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private synchronized void uploadToStorage(TodoModal modal) {
        //todo update data on database
        //MainActivity m = new MainActivity();
        //m.addItems(modal);
        helper.addNewTodo(modal);
        et_title.setText("");
        et_desc.setText("");
        et_min.setText("00");
        et_sec.setText("00");
        Toast.makeText(this, "Data to be uploaded !\n"+modal.duration, Toast.LENGTH_SHORT).show();
    }

    private synchronized void initViews() {
        et_title = findViewById(R.id.et_title);
        et_desc = findViewById(R.id.et_desc);
        et_min = findViewById(R.id.et_min);
        et_sec = findViewById(R.id.et_sec);
        min_up = findViewById(R.id.min_up);
        min_down = findViewById(R.id.min_down);
        sec_up = findViewById(R.id.sec_up);
        sec_down = findViewById(R.id.sec_down);
        upload = findViewById(R.id.upload);
        helper = new LocalDatabaseHelper(AddTodo.this);
    }
}
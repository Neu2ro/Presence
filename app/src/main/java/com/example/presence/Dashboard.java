package com.example.presence;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView recyclerview;
    ClassAdapter classAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ClassItem> classItems = new ArrayList<>();
    EditText class_edt;
    EditText subject_edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        fab = findViewById(R.id.fab_main);
        fab.setOnClickListener(v-> showDialog());

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        classAdapter = new ClassAdapter(this, classItems);
        recyclerview.setAdapter(classAdapter);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.class_dialog, null);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        class_edt = view.findViewById(R.id.className);
        subject_edt = view.findViewById(R.id.subjectName);

        Button cancel = view.findViewById(R.id.cancelBtn);
        Button add = view.findViewById(R.id.addBtn);

        cancel.setOnClickListener(v-> dialog.dismiss());
        add.setOnClickListener(v-> {
            addClass();
            dialog.dismiss();
        });



    }

    private void addClass() {
        String className = class_edt.getText().toString();
        String subjectName = subject_edt.getText().toString();
        classItems.add(new ClassItem(className, subjectName));
        classAdapter.notifyDataSetChanged();
    }
}
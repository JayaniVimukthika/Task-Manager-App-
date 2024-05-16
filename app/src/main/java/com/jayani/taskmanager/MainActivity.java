package com.jayani.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    private RecyclerView tasksRecyclerView;
    private TaskAdapter tasksAdapter;
//    private ArrayList<TaskModel> taskLists = new ArrayList<>();

    RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);

        DataBaseHelper databaseHelperClass = new DataBaseHelper(this);

        ArrayList<TaskModel> tasksLists = databaseHelperClass.getTaskList();

        tasksRecyclerView = findViewById(R.id.taskRecycler);
        tasksRecyclerView.setHasFixedSize(true);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        tasksRecyclerView.setHasFixedSize(true);
//
//        TaskAdapter taskAdaper = new TaskAdapter(this,tasksList);
        tasksAdapter =new TaskAdapter(tasksLists);
//        taskAdaper = new TaskAdapter(tasksList);
        tasksRecyclerView.setAdapter(tasksAdapter);

        tasksAdapter.setOnClickListner(new TaskAdapter.OnItemClickListner() {
            @Override
            public void onEditClick(int position) {
                int TaskId =tasksLists.get(position).getId();
                String TaskTitle =tasksLists.get(position).getTitle();
                String TaskDescription =tasksLists.get(position).getDiscription();
                String TaskDueDate =tasksLists.get(position).getDueDate();
                Intent editTask = new Intent(getApplicationContext(), editTask.class);

                editTask.putExtra("TaskId", String.valueOf(TaskId));
                editTask.putExtra("TaskTitle", TaskTitle);
                editTask.putExtra("TaskDescription", TaskDescription);
                editTask.putExtra("TaskDueDate", TaskDueDate);

                startActivity(editTask);

            }

            @Override
            public void onDeleteClick(int position) {
//                removeTask(position);
                int id =tasksLists.get(position).getId();
                databaseHelperClass.deleteTask(id);
                tasksAdapter.removeAtPosition(position);

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTask = new Intent(view.getContext(), AddNewTask.class);
                view.getContext().startActivity(addTask);}

        });

    }


    public void removeTask(int position){

    }
}
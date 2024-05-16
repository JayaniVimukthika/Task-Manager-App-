package com.jayani.taskmanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class AddNewTask extends AppCompatActivity {
    EditText title;
    EditText discription;
    EditText dueDate;
    int mYear, mMonth, mDay;
    DatePickerDialog datePickerDialog;

    Button createTask;

    private TaskAdapter tasksAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_task);


        title = findViewById(R.id.editTile);
        discription = findViewById(R.id.editDiscription);
        dueDate = findViewById(R.id.editDueDate);
        createTask =findViewById(R.id.addTask);

        dueDate.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(this,
                        (view1, year, monthOfYear, dayOfMonth) -> {
                            dueDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            datePickerDialog.dismiss();
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
            return true;
        });

        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()) {
                    DataBaseHelper db = new DataBaseHelper(AddNewTask.this);
                    TaskModel taskModel = new TaskModel(title.getText().toString(),discription.getText().toString(),dueDate.getText().toString());
                    db.addTask(taskModel);
                    tasksAdapter.notifyChange();
                    Toast.makeText(AddNewTask.this, "Add Task Successfully", Toast.LENGTH_SHORT).show();
                    finish();

//                    startActivity(getIntent());
                }

            }
        });



    }

    public boolean validateFields() {
        if(title.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter a valid title", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(discription.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter a valid description", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(dueDate.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
            return false;
        }

        else {
            return true;
        }
    }
}
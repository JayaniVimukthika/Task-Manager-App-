package com.jayani.taskmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<TaskModel> taskList;
    public SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yyyy", Locale.US);
    public SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-M-yyyy", Locale.US);
    Date date = null;
    String outputDateString = null;
    private  OnItemClickListner mListener;

    public interface OnItemClickListner{
//        void onItemClick(int position);

        void onEditClick(int position);
        void onDeleteClick(int position);

    }

    public void setOnClickListner(OnItemClickListner listner){
        mListener=listner;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_day;

        TextView item_month;
        TextView item_date;

        TextView item_title;
        TextView item_discription;
        ImageView editImg;
        ImageView deleteImg;


        ViewHolder(View view, OnItemClickListner mListener) {
            super(view);
            item_day = view.findViewById(R.id.day);
            item_date = view.findViewById(R.id.date);
            item_month = view.findViewById(R.id.month);
            item_title = view.findViewById(R.id.title);
            item_discription = view.findViewById(R.id.description);
            editImg=view.findViewById(R.id.edit);
            deleteImg=view.findViewById(R.id.delete);

            editImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mListener != null){
                        int position=getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onEditClick(position);
                        }
                    }

                }
            });

            deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mListener != null){
                        int position=getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            mListener.onDeleteClick(position);
                        }
                    }

                }
            });

        }
    }



//    public TaskAdapter(MainActivity context, ArrayList<TaskModel> taskList) {
    public TaskAdapter(ArrayList<TaskModel> taskList) {

//        this.context = context;
        this.taskList = taskList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {


        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_todo,viewGroup,false);
        ViewHolder evh = new ViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskModel task = taskList.get(position);
        holder.item_title.setText(task.getTitle());
        holder.item_discription.setText(task.getDiscription());

        try {
            date = inputDateFormat.parse(task.getDueDate());
            outputDateString = dateFormat.format(date);

            String[] items1 = outputDateString.split(" ");
            String day = items1[0];
            String dd = items1[1];
            String month = items1[2];

            holder.item_day.setText(day);
            holder.item_date.setText(dd);
            holder.item_month.setText(month);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void removeAtPosition(int position) {
        taskList.remove(position);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, taskList.size());
    }

    public void notifyChange(){
        notifyChange();
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    }




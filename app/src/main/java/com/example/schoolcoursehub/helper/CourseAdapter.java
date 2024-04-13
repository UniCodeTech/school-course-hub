package com.example.schoolcoursehub.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolcoursehub.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private List<Course> courses;
    private OnCourseCheckedChangeListener listener;
    private double totalFee = 0.0;

    public CourseAdapter(List<Course> courses, OnCourseCheckedChangeListener listener) {
        this.courses = courses;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_checkbox, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.courseNameTextView.setText(course.getCourseName());
        holder.courseFeeTextViewCheckBox.setText("Rs. "+String.valueOf(course.getCourseCost()));
        holder.checkbox.setChecked(false);
        holder.checkbox.setOnCheckedChangeListener(holder);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        public TextView courseNameTextView, courseFeeTextViewCheckBox;
        public CheckBox checkbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNameTextView = itemView.findViewById(R.id.courseNameTextViewCheckBox);
            courseFeeTextViewCheckBox = itemView.findViewById(R.id.courseFeeTextViewCheckBox);
            checkbox = itemView.findViewById(R.id.checkbox);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Course course = courses.get(getAdapterPosition());
            if (isChecked) {
                totalFee += course.getCourseCost();
            } else {
                totalFee -= course.getCourseCost();
            }
            listener.onCourseCheckedChanged(totalFee);
        }
    }


    public interface OnCourseCheckedChangeListener {
        void onCourseCheckedChanged(double totalFee);
    }



}


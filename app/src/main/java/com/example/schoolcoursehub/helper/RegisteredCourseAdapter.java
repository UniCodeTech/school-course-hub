package com.example.schoolcoursehub.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolcoursehub.R;

import java.util.List;

public class RegisteredCourseAdapter extends RecyclerView.Adapter<RegisteredCourseAdapter.CourseViewHolder> {

    private Context context;
    private List<Course> courseList;

    public RegisteredCourseAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_registered_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.bind(course);
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        private TextView courseNameTextView;
        private TextView branchNameTextView;
        private TextView courseCostTextView;
        private TextView registrationDateTextView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNameTextView = itemView.findViewById(R.id.course_name);
            branchNameTextView = itemView.findViewById(R.id.branch_name);
            courseCostTextView = itemView.findViewById(R.id.course_cost);
            registrationDateTextView = itemView.findViewById(R.id.registration_date);
        }

        public void bind(Course course) {
            courseNameTextView.setText(course.getCourseName());
            branchNameTextView.setText(course.getBranchName());
            courseCostTextView.setText(String.valueOf(course.getCourseCost()));
            registrationDateTextView.setText(course.getRegistrationDate());
        }
    }
}

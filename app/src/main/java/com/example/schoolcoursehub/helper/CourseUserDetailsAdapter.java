package com.example.schoolcoursehub.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolcoursehub.R;

import java.util.List;

public class CourseUserDetailsAdapter extends RecyclerView.Adapter<CourseUserDetailsAdapter.ViewHolder> {

    private List<CourseUserDetails> courseUserDetailsList;

    public CourseUserDetailsAdapter(List<CourseUserDetails> courseUserDetailsList) {
        this.courseUserDetailsList = courseUserDetailsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_user_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CourseUserDetails courseUserDetails = courseUserDetailsList.get(position);
        holder.textUserName.setText(courseUserDetails.getUserName());
        holder.textCourseName.setText(courseUserDetails.getCourseName());
        holder.textBranch.setText(String.valueOf(courseUserDetails.getBranchName()));
        holder.textRegistrationDate.setText(courseUserDetails.getRegistrationDate());
        holder.textTotalFee.setText(String.valueOf(courseUserDetails.getTotalFee()));

    }

    @Override
    public int getItemCount() {
        return courseUserDetailsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textUserName;
        TextView textCourseName;
        TextView textBranch;
        TextView textRegistrationDate;
        TextView textTotalFee;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textUserName = itemView.findViewById(R.id.text_user_name);
            textCourseName = itemView.findViewById(R.id.text_course_name);
            textBranch = itemView.findViewById(R.id.text_branch);
            textRegistrationDate = itemView.findViewById(R.id.text_registration_date);
            textTotalFee = itemView.findViewById(R.id.text_total_fee);
        }
    }
}

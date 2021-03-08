package com.example.prashikshan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AssignmentList extends ArrayAdapter<Assignment>
{
    private Activity context;
    private List<Assignment> assignmentList;

    public AssignmentList(Activity context, List<Assignment> assignmentList)
    {
        super(context, R.layout.assignments_list_layout, assignmentList);
        this.context = context;
        this.assignmentList = assignmentList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View assignmentListView = layoutInflater.inflate(R.layout.assignments_list_layout, null, true);
        TextView tvAssignment = (TextView)assignmentListView.findViewById(R.id.tvAssignment);
        TextView tvClass = (TextView)assignmentListView.findViewById(R.id.tvClass);
        Assignment assignment = assignmentList.get(position);
        tvAssignment.setText(assignment.getAssignmentTitle());
        tvClass.setText(assignment.getClass_name());
        return assignmentListView;
    }
}

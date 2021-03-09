package com.example.prashikshan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
        Button btnUpdate = (Button)assignmentListView.findViewById(R.id.btnUpdate);
        Assignment assignment = assignmentList.get(position);
        tvAssignment.setText(assignment.getAssignmentTitle());
        tvClass.setText(assignment.getClass_name());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditAssignment.class);
                intent.putExtra("title", assignment.getAssignmentTitle());
                intent.putExtra("problem_statement", assignment.getProblemStatement());
                intent.putExtra("class_name", assignment.getClass_name());
                intent.putExtra("institution", assignment.getInstitution());
                intent.putExtra("subject", assignment.getSubject());
                intent.putExtra("remember_me", true);
                context.startActivity(intent);
                context.finish();
            }
        });
        return assignmentListView;
    }
}

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        Button btnDelete = (Button)assignmentListView.findViewById(R.id.btnDelete);
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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert_dialog = new AlertDialog.Builder(context);
                View dialog_view = context.getLayoutInflater().inflate(R.layout.exit_dialog, null);
                Button btnExit = (Button)dialog_view.findViewById(R.id.btnExit);
                Button btnCancel = (Button)dialog_view.findViewById(R.id.btnCancel);
                TextView tvExitMessage = (TextView)dialog_view.findViewById(R.id.tvExitMessage);

                alert_dialog.setView(dialog_view);
                AlertDialog alertDialog = alert_dialog.create();
                alert_dialog.setCancelable(false);
                tvExitMessage.setText(R.string.delete_message);

                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Assignments");
                        assignment.setActive(false);
                        String record_name = assignment.getInstitution() + "_" + assignment.getSubject() + "_" + assignment.getAssignmentTitle();
                        mDatabase.child(record_name).setValue(assignment)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(context, "Assignment deleted", Toast.LENGTH_SHORT).show();
                                            context.recreate();
                                        }
                                        else
                                        {
                                            Toast.makeText(context, "Assignment could not be deleted", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        return assignmentListView;
    }
}

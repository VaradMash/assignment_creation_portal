package com.example.prashikshan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.server.FavaDiagnosticsEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AssignmentsFragment extends Fragment {
    DatabaseReference mDatabase;
    List<Assignment> assignmentList;
    ListView listViewAssignments;
    Assignment current_assignment;
    ProgressBar assignmentsProgressBar;
    Faculty current_user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_assignments, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listViewAssignments = (ListView)view.findViewById(R.id.listViewAssignments);
        assignmentList = new ArrayList<>();
        assignmentsProgressBar = (ProgressBar)view.findViewById(R.id.assignmentsProgressBar);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        FirebaseDatabase.getInstance().getReference().child("Faculty")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot faculty: snapshot.getChildren())
                        {
                            current_user = faculty.getValue(Faculty.class);
                            if(current_user.getEmail().equals(email))
                            {
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), "Error occurred !", Toast.LENGTH_SHORT).show();
                    }
                });
        mDatabase.child("Assignments").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    for(DataSnapshot assignment: task.getResult().getChildren())
                    {
                        current_assignment = assignment.getValue(Assignment.class);
                        if(current_assignment.getInstitution().equals(current_user.getInstitution()) && current_assignment.getClass_name().equals(current_user.getClass_name()) && current_assignment.isActive() && current_assignment.getSubject().equals(current_user.getSubject()))
                        {
                            assignmentList.add(current_assignment);
                        }
                    }
                    AssignmentList adapter = new AssignmentList(getActivity(), assignmentList);
                    listViewAssignments.setAdapter(adapter);
                    assignmentsProgressBar.setVisibility(View.GONE);
                    if(assignmentList.isEmpty())
                    {
                        Toast.makeText(getActivity(), "No assignments found!", Toast.LENGTH_SHORT).show();
                        assignmentsProgressBar.setVisibility(View.GONE);
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "No Assignments found !", Toast.LENGTH_SHORT).show();
                    assignmentsProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}

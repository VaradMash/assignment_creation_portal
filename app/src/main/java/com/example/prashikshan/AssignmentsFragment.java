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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AssignmentsFragment extends Fragment {
    DatabaseReference mDatabase;
    List<Faculty> assignmentList;
    ListView listViewAssignments;
    ProgressBar assignmentsProgressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_assignments, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Faculty");
        listViewAssignments = (ListView)view.findViewById(R.id.listViewAssignments);
        assignmentList = new ArrayList<>();
        assignmentsProgressBar = (ProgressBar)view.findViewById(R.id.assignmentsProgressBar);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                assignmentList.clear();
                for(DataSnapshot userSnapshot: snapshot.getChildren())
                {
                    Faculty faculty = userSnapshot.getValue(Faculty.class);
                    assignmentList.add(faculty);
                }
                AssignmentList adapter = new AssignmentList(getActivity(), assignmentList);
                listViewAssignments.setAdapter(adapter);
                assignmentsProgressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error occured !", Toast.LENGTH_SHORT).show();
                assignmentsProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
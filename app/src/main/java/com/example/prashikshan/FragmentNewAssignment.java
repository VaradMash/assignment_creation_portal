package com.example.prashikshan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentNewAssignment extends Fragment
{
    EditText etTitle, etProblemStatement;
    Button btnAssign;
    Faculty current_faculty;
    DatabaseReference assignmentReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_assignment, null, true);

        etTitle = (EditText)view.findViewById(R.id.etTitle);
        etProblemStatement = (EditText)view.findViewById(R.id.etProblemStatement);
        btnAssign = (Button)view.findViewById(R.id.btnAssign);
        assignmentReference = FirebaseDatabase.getInstance().getReference().child("Assignments");

        btnAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String problemStatement = etProblemStatement.getText().toString();
                if(title.isEmpty() || problemStatement.isEmpty())
                {
                    if(title.isEmpty())
                    {
                        etTitle.setError("Title cannot be empty !");
                        etTitle.requestFocus();
                    }
                    if(problemStatement.isEmpty())
                    {
                        etProblemStatement.setError("Problem Statement cannot be empty !");
                        etProblemStatement.requestFocus();
                    }
                }
                else
                {
                    Assignment assignment = new Assignment(title, problemStatement, current_faculty.getClass_name(), current_faculty.getInstitution(), current_faculty.getSubject());
                    String record_name = current_faculty.getSubject() + "_" + title;
                    assignmentReference.child(record_name).setValue(assignment);
                    Toast.makeText(getActivity(), "Assignment Posted !", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                        for(DataSnapshot faculty:snapshot.getChildren())
                        {
                            current_faculty = faculty.getValue(Faculty.class);
                            if(current_faculty.getEmail().equals(email))
                            {
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}

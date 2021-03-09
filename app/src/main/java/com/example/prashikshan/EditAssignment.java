package com.example.prashikshan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditAssignment extends AppCompatActivity {

    TextView etEditTitle;
    EditText etEditProblemStatement;
    Button btnUpdateAssignment;
    String title, problem_statement, class_name, institution, subject;
    boolean remember_me;
    DatabaseReference mDatabase;

    @Override
    public void onBackPressed() {
        /*
         * Input : None
         * Utility : Return to user profile.
         * Output : Switch to User profile.
         */
        Intent intent = new Intent(getApplicationContext(), UserProfile.class);
        intent.putExtra("remember_me", remember_me);
        startActivity(intent);
        EditAssignment.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);
        //Disable night mode for activity.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        etEditTitle = (TextView)findViewById(R.id.etEditTitle);
        etEditProblemStatement = (EditText)findViewById(R.id.etEditProblemStatement);
        btnUpdateAssignment = (Button) findViewById(R.id.btnUpdateAssignment);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Assignments");

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        problem_statement = intent.getStringExtra("problem_statement");
        class_name = intent.getStringExtra("class_name");
        institution = intent.getStringExtra("institution");
        remember_me = intent.getBooleanExtra("remember_me", true);
        subject = intent.getStringExtra("subject");
        etEditTitle.setText(title);
        etEditProblemStatement.setText(problem_statement);

        btnUpdateAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String problem_statement = etEditProblemStatement.getText().toString();
                if(problem_statement.isEmpty())
                {
                    etEditProblemStatement.setError("Problem statement cannot be empty !");
                    etEditProblemStatement.requestFocus();
                }
                else
                {
                    String record_name = subject + "_" + title;
                    Assignment assignment = new Assignment(title, problem_statement, class_name, institution, subject);
                    mDatabase.child(record_name).setValue(assignment)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(), "Assignment updated !", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Assignment could not be updated !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
package com.example.androidportfolio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.androidportfolio.models.Portfolio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_DETAILS_CODE = 1337;
    public static final String TAG = "MainActivity";

    private String githubUserName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addDetailsButton = findViewById(R.id.btn_add_details);

        addDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent,REQUEST_DETAILS_CODE);
            }
        });
    }

    public void openGithub(View view){
        String githubUrl = "https://github.com/";
        if (githubUserName != null){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://github.com/"+githubUserName));
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this, "No Github Username Found!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_DETAILS_CODE && resultCode==RESULT_OK && data!=null){
            Portfolio portfolio = data.getParcelableExtra(AddActivity.PARAM_PORTFOLIO);
            if (portfolio!=null){
                setDetails(portfolio);
            }
        }
    }

    private void setDetails(@NonNull Portfolio portfolio){
        Log.i(TAG,String.valueOf(portfolio));

        TextView textViewName = findViewById(R.id.tv_name);
        TextView textViewPosition = findViewById(R.id.tv_title);
        TextView textViewEducationTitle = findViewById(R.id.tv_education_title);
        TextView textViewEducationDegree = findViewById(R.id.tv_education_degree);
        TextView textViewEducationYear = findViewById(R.id.tv_education_year);
        TextView textViewCourseTitle = findViewById(R.id.tv_course_title);
        TextView textViewCourseDegree = findViewById(R.id.tv_course_degree);
        TextView textViewCourseYear = findViewById(R.id.tv_course_year);

        textViewName.setText(portfolio.getName());
        textViewPosition.setText(portfolio.getPosition());
        textViewEducationTitle.setText(portfolio.getEducation().getTitle());
        textViewEducationDegree.setText(portfolio.getEducation().getDegree());
        textViewEducationYear.setText(portfolio.getEducation().getYear());
        textViewCourseTitle.setText(portfolio.getCourse().getOrganization());
        textViewCourseDegree.setText(portfolio.getCourse().getName());
        textViewCourseYear.setText(portfolio.getCourse().getYear());

        githubUserName = portfolio.getGithubUserName();
    }
}
package com.example.ais.pocketadmin.Teachers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ais.pocketadmin.Adapters.TeachersGridAdapter;
import com.example.ais.pocketadmin.R;
import com.example.ais.pocketadmin.Teachers.Database.DatabaseHandler;
import com.example.ais.pocketadmin.Teachers.TeacherAssistant.About;
import com.example.ais.pocketadmin.Teachers.TeacherAssistant.SettingsActivity;
import com.example.ais.pocketadmin.Welcome.Logins.LoginActivityMain;
import com.example.ais.pocketadmin.Welcome.Logins.LoginTypes.LoginActivityTeahcers;

import java.util.ArrayList;
//TODO REference to the context http://www.genuinecoder.com/2016/04/android-app-for-teachers-with-source.html/
public class TeacherMainActivity extends AppCompatActivity {
    ArrayList<String> basicFields;
    TeachersGridAdapter adapter;
    public static ArrayList<String> divisions, stream;
    GridView gridView;
    public static DatabaseHandler handler;
    public static Activity activity;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mai_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        basicFields = new ArrayList<>();
        handler = new DatabaseHandler(this);
        activity = this;
        getSupportActionBar().show();



        gridView = (GridView)findViewById(R.id.grid);
        basicFields.add("Attendance");
        basicFields.add("Scheduler");
        basicFields.add("Notes");
        basicFields.add("Students");
        basicFields.add("CGPA Caluclator");
        basicFields.add("Marks");
        adapter = new TeachersGridAdapter(this,basicFields);
        gridView.setAdapter(adapter);
    }
    public void loadSettings(MenuItem item) {
        Intent launchIntent = new Intent(this,SettingsActivity.class);
        startActivity(launchIntent);
    }
    public void loadAbout(MenuItem item) {
        Intent launchIntent = new Intent(this,About.class);
        startActivity(launchIntent);
    } public void loadLogout(MenuItem item) {
        SharedPreferences sharedPrefs = getSharedPreferences(LoginActivityTeahcers.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.apply();

        Intent logout = new Intent(this, LoginActivityMain.class);
        startActivity(logout);
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Press Logout to exit.",Toast.LENGTH_SHORT).show();
    }
}
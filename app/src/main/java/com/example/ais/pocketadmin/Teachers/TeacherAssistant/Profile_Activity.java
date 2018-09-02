package com.example.ais.pocketadmin.Teachers.TeacherAssistant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ais.pocketadmin.R;
import com.example.ais.pocketadmin.Teachers.Database.DatabaseHandler;
import com.example.ais.pocketadmin.Teachers.TeacherMainActivity;
import com.example.ais.pocketadmin.Volley.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Profile_Activity extends AppCompatActivity{

    DatabaseHandler handler = TeacherMainActivity.handler;
    Activity profileActivity = this;
    ListView listView;
    Profile_Adapter adapter;
    ArrayList<String> dates;
    ArrayList<String> datesALONE;
    ArrayList<Integer> hourALONE;
    ArrayList<Boolean> atts;
    Activity activity =this;
    private View v;


    //STRINGS
    public String f_name,l_name,s_usn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_profile);
        dates = new ArrayList<>();
        datesALONE = new ArrayList<>();
        hourALONE = new ArrayList<>();
        atts = new ArrayList<>();

        listView = (ListView) findViewById(R.id.attendProfileView_list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = new Intent(profileActivity,Student_Registartion.class);
                startActivity(launchIntent);
            }
        });

        TextView textView = (TextView)findViewById(R.id.profileContentView);
        assert textView != null;
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setTitle("Delete Student");
                alert.setMessage("Are you sure ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = (EditText) findViewById(R.id.editText);
                        String regno = editText.getText().toString();
                        String qu = "DELETE FROM STUDENT WHERE REGNO = '" + regno.toUpperCase() + "'";
                        if (TeacherMainActivity.handler.execAction(qu)) {
                            Log.d("delete", "done from student");
                            String qa = "DELETE FROM ATTENDANCE WHERE register = '" + regno.toUpperCase() + "'";
                            if (TeacherMainActivity.handler.execAction(qa)) {
                                Toast.makeText(getBaseContext(), "Deleted", Toast.LENGTH_LONG).show();
                                Log.d("delete", "done from attendance");
                            }
                        }
                    }
                });
                alert.setNegativeButton("No", null);
                alert.show();
                return true;
            }
        });


        Button findButton = (Button)findViewById(R.id.buttonFind);
        assert findButton != null;
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find(v);
            }
        });
    }

    public void find(View view) {
        dates.clear();
        atts.clear();
        EditText editText = (EditText)findViewById(R.id.editText);
        TextView textView = (TextView)findViewById(R.id.profileContentView);
        String reg = editText.getText().toString().toUpperCase();
        String user_data = Config.STUDENT_DATA + editText;
        StringRequest stringRequest = new StringRequest(user_data, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray(Config.DATA_JSON_ARRAY);
                    JSONObject collegeData = result.getJSONObject(0);
                    f_name = collegeData.getString(Config.KEY_STUDENT_FNAME);
                    l_name = collegeData.getString(Config.KEY_STUDENT_LNAME);
                    s_usn = collegeData.getString(Config.KEY_STUDENT_USN);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile_Activity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



        String qu = "SELECT * FROM STUDENT WHERE regno = '" + reg.toUpperCase() + "'";
        String qc = "SELECT * FROM ATTENDANCE WHERE register = '" + reg.toUpperCase() + "';";
        String qd = "SELECT * FROM ATTENDANCE WHERE register = '" + reg.toUpperCase() + "' AND isPresent = 1";
        Cursor cursor = handler.execQuery(qu);
        //Start Count Here

        float att = 0f;
        Cursor cur = handler.execQuery(qc);
        Cursor cur1 = handler.execQuery(qd);
        if(cur==null)
        {
            Log.d("profile","cur null");
        }
        if(cur1==null)
        {
            Log.d("profile","cur1 null");
        }
        if(cur!=null&&cur1!=null)
        {
            cur.moveToFirst();
            cur1.moveToFirst();
            try{
                att = ((float) cur1.getCount()/cur.getCount())*100;
                if(att<=0)
                    att = 0f;
                Log.d("Profile_Activity","Total = " + cur.getCount() + " avail = "+cur1.getCount() + " per " + att);
            }catch (Exception e){att = -1;}
        }


        if(cursor==null||cursor.getCount()==0)
        {
            assert textView != null;
            textView.setText("No Data Available");
        }else
        {
            String attendance = "";
            if(att<0)
            {
                attendance = "Attendance Not Available";
            }else
                attendance = " Attendance " + att + " %";
            cursor.moveToFirst();
            String buffer = "";
            buffer += " " + cursor.getString(0) + "\n";
            buffer += " " + cursor.getString(1)+ "\n";
            buffer += " " + cursor.getString(2)+ "\n";
            buffer += " " + cursor.getString(3)+ "\n";
            buffer += " " + cursor.getInt(4)+ "\n";
            buffer += " " + attendance+ "\n";
            textView.setText(buffer);

            String q = "SELECT * FROM ATTENDANCE WHERE register = '" + editText.getText().toString().toUpperCase() + "'";
            Cursor cursorx = handler.execQuery(q);
            if(cursorx==null||cursorx.getCount()==0)
            {
                Toast.makeText(getBaseContext(),"No Attendance Info Available",Toast.LENGTH_LONG).show();
            }else
            {
                cursorx.moveToFirst();
                while(!cursorx.isAfterLast())
                {
                    datesALONE.add(cursorx.getString(0));
                    hourALONE.add(cursorx.getInt(1));
                    dates.add(cursorx.getString(0) + ":" + cursorx.getInt(1) + "th Hour");
                    if(cursorx.getInt(3)==1)
                        atts.add(true);
                    else {
                        Log.d("profile",cursorx.getString(0) + " -> " + cursorx.getInt(3));
                        atts.add(false);
                    }
                    cursorx.moveToNext();
                }
                adapter = new Profile_Adapter(dates,atts,profileActivity,datesALONE,hourALONE,editText.getText().toString().toUpperCase());
                listView.setAdapter(adapter);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return true;
    }


    public void editStudent(MenuItem item) {
        Intent launchIntent  = new Intent(this,Edit_Student.class);
        startActivity(launchIntent);
    }
}
/*
*
*
* public class Profile_Activity extends AppCompatActivity{

    DatabaseHandler handler = TeacherMainActivity.handler;
    Activity profileActivity = this;
    ListView listView;
    Profile_Adapter adapter;
    ArrayList<String> dates;
    ArrayList<String> datesALONE;
    ArrayList<Integer> hourALONE;
    ArrayList<Boolean> atts;
    Activity activity =this;
    private View v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_profile);
        dates = new ArrayList<>();
        datesALONE = new ArrayList<>();
        hourALONE = new ArrayList<>();
        atts = new ArrayList<>();

        listView = (ListView) findViewById(R.id.attendProfileView_list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = new Intent(profileActivity,Student_Registartion.class);
                startActivity(launchIntent);
            }
        });

        TextView textView = (TextView)findViewById(R.id.profileContentView);
        assert textView != null;
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setTitle("Delete Student");
                alert.setMessage("Are you sure ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = (EditText) findViewById(R.id.editText);
                        String regno = editText.getText().toString();
                        String qu = "DELETE FROM STUDENT WHERE REGNO = '" + regno.toUpperCase() + "'";
                        if (TeacherMainActivity.handler.execAction(qu)) {
                            Log.d("delete", "done from student");
                            String qa = "DELETE FROM ATTENDANCE WHERE register = '" + regno.toUpperCase() + "'";
                            if (TeacherMainActivity.handler.execAction(qa)) {
                                Toast.makeText(getBaseContext(), "Deleted", Toast.LENGTH_LONG).show();
                                Log.d("delete", "done from attendance");
                            }
                        }
                    }
                });
                alert.setNegativeButton("No", null);
                alert.show();
                return true;
            }
        });


        Button findButton = (Button)findViewById(R.id.buttonFind);
        assert findButton != null;
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find(v);
            }
        });
    }

    public void find(View view) {
        dates.clear();
        atts.clear();
        EditText editText = (EditText)findViewById(R.id.editText);
        TextView textView = (TextView)findViewById(R.id.profileContentView);
        String reg = editText.getText().toString();
        String qu = "SELECT * FROM STUDENT WHERE regno = '" + reg.toUpperCase() + "'";
        String qc = "SELECT * FROM ATTENDANCE WHERE register = '" + reg.toUpperCase() + "';";
        String qd = "SELECT * FROM ATTENDANCE WHERE register = '" + reg.toUpperCase() + "' AND isPresent = 1";
        Cursor cursor = handler.execQuery(qu);
        //Start Count Here

        float att = 0f;
        Cursor cur = handler.execQuery(qc);
        Cursor cur1 = handler.execQuery(qd);
        if(cur==null)
        {
            Log.d("profile","cur null");
        }
        if(cur1==null)
        {
            Log.d("profile","cur1 null");
        }
        if(cur!=null&&cur1!=null)
        {
            cur.moveToFirst();
            cur1.moveToFirst();
            try{
                att = ((float) cur1.getCount()/cur.getCount())*100;
                if(att<=0)
                    att = 0f;
                Log.d("Profile_Activity","Total = " + cur.getCount() + " avail = "+cur1.getCount() + " per " + att);
            }catch (Exception e){att = -1;}
        }


        if(cursor==null||cursor.getCount()==0)
        {
            assert textView != null;
            textView.setText("No Data Available");
        }else
        {
            String attendance = "";
            if(att<0)
            {
                attendance = "Attendance Not Available";
            }else
                attendance = " Attendance " + att + " %";
            cursor.moveToFirst();
            String buffer = "";
            buffer += " " + cursor.getString(0) + "\n";
            buffer += " " + cursor.getString(1)+ "\n";
            buffer += " " + cursor.getString(2)+ "\n";
            buffer += " " + cursor.getString(3)+ "\n";
            buffer += " " + cursor.getInt(4)+ "\n";
            buffer += " " + attendance+ "\n";
            textView.setText(buffer);

            String q = "SELECT * FROM ATTENDANCE WHERE register = '" + editText.getText().toString().toUpperCase() + "'";
            Cursor cursorx = handler.execQuery(q);
            if(cursorx==null||cursorx.getCount()==0)
            {
                Toast.makeText(getBaseContext(),"No Attendance Info Available",Toast.LENGTH_LONG).show();
            }else
            {
                cursorx.moveToFirst();
                while(!cursorx.isAfterLast())
                {
                    datesALONE.add(cursorx.getString(0));
                    hourALONE.add(cursorx.getInt(1));
                    dates.add(cursorx.getString(0) + ":" + cursorx.getInt(1) + "th Hour");
                    if(cursorx.getInt(3)==1)
                        atts.add(true);
                    else {
                        Log.d("profile",cursorx.getString(0) + " -> " + cursorx.getInt(3));
                        atts.add(false);
                    }
                    cursorx.moveToNext();
                }
                adapter = new Profile_Adapter(dates,atts,profileActivity,datesALONE,hourALONE,editText.getText().toString().toUpperCase());
                listView.setAdapter(adapter);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return true;
    }


    public void editStudent(MenuItem item) {
        Intent launchIntent  = new Intent(this,Edit_Student.class);
        startActivity(launchIntent);
    }
}
* */
package com.example.ais.pocketadmin.Teachers.TeacherAssistant;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ais.pocketadmin.R;
import com.example.ais.pocketadmin.Teachers.Database.EducationPrograms;
import com.example.ais.pocketadmin.Volley.Config;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.ais.pocketadmin.Volley.Config.KEY_PARENT_PASS;
import static com.example.ais.pocketadmin.Volley.Config.KEY_PARENT_PH;
import static com.example.ais.pocketadmin.Volley.Config.KEY_STUDENT_BRANCH;
import static com.example.ais.pocketadmin.Volley.Config.KEY_STUDENT_FNAME;
import static com.example.ais.pocketadmin.Volley.Config.KEY_STUDENT_LNAME;
import static com.example.ais.pocketadmin.Volley.Config.KEY_STUDENT_PASS;
import static com.example.ais.pocketadmin.Volley.Config.KEY_STUDENT_PH;
import static com.example.ais.pocketadmin.Volley.Config.KEY_STUDENT_ROLL;
import static com.example.ais.pocketadmin.Volley.Config.KEY_STUDENT_STREAM;
import static com.example.ais.pocketadmin.Volley.Config.KEY_STUDENT_USN;

public class Student_Registartion extends AppCompatActivity implements View.OnClickListener {

    private static final String REGISTER_URL = Config.STUDENT_REGISTER_URL;
    @Bind(R.id.student_fname) EditText student_fname;
    @Bind(R.id.student_lname) EditText student_lname;
    @Bind(R.id.student_roll) EditText student_roll;
    @Bind(R.id.student_usn) EditText student_usn;
    @Bind(R.id.student_ph) EditText student_ph;
    @Bind(R.id.parent_ph) EditText parent_ph;
    @Bind(R.id.buttonSAVE) Button btn;
    @Bind(R.id.spinner_stream) Spinner spinner_stream;
    @Bind(R.id.spinner_branch) Spinner spinner_branch;
    public String selected_programm,selected_branch;


    Activity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__registartion);
        ButterKnife.bind(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, EducationPrograms.stream);
        spinner_stream.setAdapter(adapter);
        spinner_stream.setPrompt("Please Select One");

        spinner_stream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_programm = spinner_stream.getSelectedItem().toString().trim();
                if(selected_programm == "Administration"){
                    ArrayAdapter<String> adapt = new ArrayAdapter<String>(Student_Registartion.this, android.R.layout.simple_spinner_dropdown_item, EducationPrograms.Administration);
                    spinner_branch.setAdapter(adapt);
                }
                else if(selected_programm == "Architecture"){
                    ArrayAdapter<String> adapt = new ArrayAdapter<String>(Student_Registartion.this, android.R.layout.simple_spinner_dropdown_item, EducationPrograms.Architecture);
                    spinner_branch.setAdapter(adapt);
                }
                else if(selected_programm == "Arts"){
                    ArrayAdapter<String> adapt = new ArrayAdapter<String>(Student_Registartion.this, android.R.layout.simple_spinner_dropdown_item, EducationPrograms.Arts);
                    spinner_branch.setAdapter(adapt);
                }
                else if(selected_programm == "BusinessStudies"){
                    ArrayAdapter<String> adapt = new ArrayAdapter<String>(Student_Registartion.this, android.R.layout.simple_spinner_dropdown_item, EducationPrograms.BusinessStudies);
                    spinner_branch.setAdapter(adapt);
                }
                else if(selected_programm == "Design"){
                    ArrayAdapter<String> adapt = new ArrayAdapter<String>(Student_Registartion.this, android.R.layout.simple_spinner_dropdown_item, EducationPrograms.Design);
                    spinner_branch.setAdapter(adapt);
                }
                else if(selected_programm == "Engineering"){
                    ArrayAdapter<String> adapt = new ArrayAdapter<String>(Student_Registartion.this, android.R.layout.simple_spinner_dropdown_item, EducationPrograms.Engineering);
                    spinner_branch.setAdapter(adapt);
                }

                //selected_branch = spinner_branch.getSelectedItem().toString().trim();
            }
            public String getSpin(String selected_programm) {
                return selected_programm;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });assert btn != null;
        btn.setOnClickListener(this);
    }

    private void registerUser(){
        final String fname = student_fname.getText().toString().trim();
        final String lname = student_lname.getText().toString().trim();
        final String roll = student_roll.getText().toString().trim();
        final String usn = student_usn.getText().toString().toUpperCase().trim();
        final String s_phone = student_ph.getText().toString().trim();
        final String p_phone = parent_ph.getText().toString().trim();
        final String s_stream = spinner_stream.getSelectedItem().toString().trim();
        final String s_branch = spinner_branch.getSelectedItem().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Student_Registartion.this,response,Toast.LENGTH_LONG).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Student_Registartion.this,error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_STUDENT_USN,usn);
                params.put(KEY_STUDENT_ROLL,roll);
                params.put(KEY_STUDENT_PH, s_phone);
                params.put(KEY_STUDENT_FNAME,fname);
                params.put(KEY_STUDENT_LNAME,lname);
                params.put(KEY_PARENT_PH, p_phone);
                params.put(KEY_STUDENT_PASS,s_phone);
                params.put(KEY_PARENT_PASS,p_phone);
                params.put(KEY_STUDENT_STREAM,s_stream);
                params.put(KEY_STUDENT_BRANCH,s_branch);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if(v == btn){
            registerUser();
        }
    }
}


/*

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, TeacherMainActivity.divisions);
        spinner.setAdapter(adapter);

        Button btn = (Button) findViewById(R.id.buttonSAVE);
        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDatabase(v);
            }
        });
    }


    public void saveToDatabase(View view) {
        EditText name = (EditText)findViewById(R.id.student_fname);
        EditText roll = (EditText)findViewById(R.id.roll);
        EditText student_usn = (EditText)findViewById(R.id.student_usn);
        EditText student_ph = (EditText)findViewById(R.id.student_ph);
        String classSelected = spinner.getSelectedItem().toString();

        if(name.getText().length()<2||roll.getText().length()==0||student_usn.getText().length()<2||
                student_ph.getText().length()<2||classSelected.length()<2)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            alert.setTitle("Invalid");
            alert.setMessage("Insufficient Data");
            alert.setPositiveButton("OK", null);
            alert.show();
            return;
        }

        String qu = "INSERT INTO STUDENT VALUES('" +name.getText().toString()+ "'," +
                "'" + classSelected +"',"+
                "'" + student_usn.getText().toString().toUpperCase() +"',"+
                "'" + student_ph.getText().toString() +"',"+
                "" + Integer.parseInt(roll.getText().toString()) +");";
        Log.d("Student Reg" , qu);
        TeacherMainActivity.handler.execAction(qu);
        qu = "SELECT * FROM STUDENT WHERE regno = '" + student_usn.getText().toString() +  "';";
        Log.d("Student Reg" , qu);
        if(TeacherMainActivity.handler.execQuery(qu)!=null)
        {
            Toast.makeText(getBaseContext(),"Student Added", Toast.LENGTH_LONG).show();
            this.finish();
        }
    }
}
*/
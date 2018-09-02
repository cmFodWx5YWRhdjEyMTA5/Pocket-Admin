package com.example.ais.pocketadmin.Volley;

/**
 * Created by AIS on 07-Jul-17.
 */

public class Config {
    public static final String ip = "192.168.0.103";
    //Student registration
    public static final String KEY_STUDENT_USN = "student_usn";
    public static final String KEY_STUDENT_ROLL = "student_roll";
    public static final String KEY_STUDENT_PH = "student_ph";
    public static final String KEY_STUDENT_FNAME = "student_fname";
    public static final String KEY_STUDENT_LNAME = "student_lname";
    public static final String KEY_PARENT_PH = "parent_ph";
    public static final String KEY_STUDENT_PASS = "student_pass";
    public static final String KEY_PARENT_PASS = "parent_pass";
    public static final String KEY_STUDENT_STREAM ="stream_id";
    public static final String KEY_STUDENT_BRANCH ="branch_id";

    public static final String STUDENT_REGISTER_URL = "http://" + ip + "/pocketShiksha//studentRegistration.php";
    public static final String STUDENT_DATA = "http://" + ip + "/pocketShiksha//StudentData.php?student_usn=";
    public static final String DATA_JSON_ARRAY = "result";
}

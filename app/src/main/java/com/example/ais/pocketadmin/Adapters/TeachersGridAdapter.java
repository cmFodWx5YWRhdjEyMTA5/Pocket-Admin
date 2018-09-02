package com.example.ais.pocketadmin.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ais.pocketadmin.R;
import com.example.ais.pocketadmin.Teachers.TeacherAssistant.AttendanceActivity;
import com.example.ais.pocketadmin.Teachers.TeacherAssistant.Cgpa_Activity;
import com.example.ais.pocketadmin.Teachers.TeacherAssistant.Marks;
import com.example.ais.pocketadmin.Teachers.TeacherAssistant.NoteActivity;
import com.example.ais.pocketadmin.Teachers.TeacherAssistant.Profile_Activity;
import com.example.ais.pocketadmin.Teachers.TeacherAssistant.Scheduler;
import com.example.ais.pocketadmin.Teachers.TeacherMainActivity;

import java.util.ArrayList;

/**
 * Created by AIS on 05-Jul-17.
 */

public class TeachersGridAdapter extends BaseAdapter {
    ArrayList names;
    public static Activity activity;
    public TeachersGridAdapter(Activity activity, ArrayList names) {
        this.activity = activity;
        this.names = names;
    }
    @Override
    public int getCount() {
        return names.size();
    }
    @Override
    public Object getItem(int position) {
        return names.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(activity);
            v = vi.inflate(R.layout.grid_layout, null);
        }
        TextView textView = (TextView)v.findViewById(R.id.namePlacer);
        ImageView imageView = (ImageView)v.findViewById(R.id.imageHolder);
        if(names.get(position).toString().equals("Attendance"))
        {
            imageView.setImageResource(R.drawable.ic_attendance);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = activity.getFragmentManager();
                    createRequest request = new createRequest();
                    request.show(fm,"Select");
                }
            });
        }else if(names.get(position).toString().equals("Scheduler"))
        {
            imageView.setImageResource(R.drawable.ic_schedule);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launchinIntent = new Intent(activity, Scheduler.class);
                    activity.startActivity(launchinIntent);
                }
            });
        }else if(names.get(position).toString().equals("Notes"))
        {
            imageView.setImageResource(R.drawable.ic_notes);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launchinIntent = new Intent(activity, NoteActivity.class);
                    activity.startActivity(launchinIntent);
                }
            });
        }else if(names.get(position).toString().equals("Students"))
        {
            imageView.setImageResource(R.drawable.ic_profile);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launchinIntent = new Intent(activity, Profile_Activity.class);
                    activity.startActivity(launchinIntent);
                }
            });
        }
        else if(names.get(position).toString().equals("CGPA Caluclator"))
        {
            imageView.setImageResource(R.drawable.ic_cgpa);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launchinIntent = new Intent(activity, Cgpa_Activity.class);
                    activity.startActivity(launchinIntent);
                }
            });
        }else if(names.get(position).toString().equals("Marks"))
        {
            imageView.setImageResource(R.drawable.ic_marks);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launchinIntent = new Intent(activity, Marks.class);
                    activity.startActivity(launchinIntent);
                }
            });
        }
        textView.setText(names.get(position).toString());
        return v;
    }
    public static class createRequest extends DialogFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View v = inflater.inflate(R.layout.pick_period, null);
            final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
            final EditText hour = (EditText)v.findViewById(R.id.periodID);
            final Spinner spn = (Spinner) v.findViewById(R.id.spinnerSubject);
            String qu = "SELECT DISTINCT sub FROM NOTES";
            ArrayList<String> subs = new ArrayList<>();
            subs.add("Not Specified");
            Cursor cr = TeacherMainActivity.handler.execQuery(qu);
            if(cr!=null)
            {
                cr.moveToFirst();
                while(!cr.isAfterLast()) {
                    subs.add(cr.getString(0));
                    Log.d("TeachersGridAdapter.class", "Cached " + cr.getString(0));
                    cr.moveToNext();
                }
            }else
                Log.d("TeachersGridAdapter.class", "No SUBS" + cr.getString(0));
            ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, subs);
            assert spn != null;
            spn.setAdapter(adapterSpinner);
            builder.setView(v)
                    // Add action buttons
                    .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            int day = datePicker.getDayOfMonth();
                            int month = datePicker.getMonth() + 1;
                            int year = datePicker.getYear();
                            String date = year + "-" + month + "-" + day;
                            String subject = spn.getSelectedItem().toString();
                            String qx = "SELECT title FROM NOTES where sub = '" + subject + "'";
                            Cursor cr = TeacherMainActivity.handler.execQuery(qx);
                            String subnames = "";
                            if(cr!=null)
                            {
                                cr.moveToFirst();
                                while(!cr.isAfterLast()) {
                                    subnames += (cr.getString(0)) + "n";
                                    cr.moveToNext();
                                }
                            }
                            makeNotification(subnames);
                            Cursor cursor = TeacherMainActivity.handler.execQuery("SELECT * FROM ATTENDANCE WHERE datex = '" +
                                    date +"' AND hour = " + hour.getText() + ";");
                            if(cursor==null||cursor.getCount()==0)
                            {
                                Intent launchinIntent = new Intent(TeacherMainActivity.activity, AttendanceActivity.class);
                                launchinIntent.putExtra("DATE", date);
                                launchinIntent.putExtra("PERIOD", hour.getText().toString());
                                TeacherMainActivity.activity.startActivity(launchinIntent);
                            }else {
                                Toast.makeText(getActivity(),"Period Already Added",Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            return builder.create();
        }
    }
    public static void makeNotification(String userIntrouble) {
        Log.d("NOTIFICATION","Building..........");
        Intent notificationIntent = new Intent(activity.getApplicationContext(), NoteActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(activity, 0, notificationIntent,
                0);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity.getBaseContext());
        Uri ring = Uri.parse(sharedPrefs.getString("Notification_Sound", Settings.System.DEFAULT_RINGTONE_URI.toString()));
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity.getBaseContext())
                .setTicker("Ticker Title").setContentTitle("Notes Are Available For this subject")
                .setSmallIcon(R.drawable.ic_notes)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(userIntrouble))
                .setContentIntent(pIntent)
                .setSound(ring);
        Notification noti = builder.build();
        noti.contentIntent = pIntent;
        noti.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(activity.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);
    }
}
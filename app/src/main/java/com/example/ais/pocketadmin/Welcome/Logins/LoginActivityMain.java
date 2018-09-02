package com.example.ais.pocketadmin.Welcome.Logins;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ais.pocketadmin.R;
import com.example.ais.pocketadmin.Welcome.Logins.LoginTypes.LoginActivityAdmin;
import com.example.ais.pocketadmin.Welcome.Logins.LoginTypes.LoginActivityTeahcers;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivityMain extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.teacher_btn)
    Button teacher_btn;
    @Bind(R.id.admin_btn) Button admin_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity_login_main);
        ButterKnife.bind(this);
        isConnected(this);
        teacher_btn.setOnClickListener(this);
        admin_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v==teacher_btn){
            Intent teacher = new Intent(this, LoginActivityTeahcers.class);
            startActivity(teacher);
        }
        else if(v==admin_btn){
            Intent admin = new Intent(this, LoginActivityAdmin.class);
            startActivity(admin);
        }

    }
    public boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())) {
            return true;
        }else{
            showDialog();
            return false;
        }
    }
    private void showDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Connect to wifi or quit")
                .setCancelable(false)
                .setPositiveButton("Connect to WIFI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}

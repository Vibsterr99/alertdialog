package com.example.alertdialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textv;
    SharedPreferences sp;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.English){
            setlang("English");

        }
        else if(item.getItemId()==R.id.Hindi){
            setlang("Hindi");
        }


        return true;
    }

    public void setlang(String lang){

        try {
            sp.edit().putString("language",ObjectSerializer.serialize(lang)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }

        textv.setText(lang);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textv=(TextView)findViewById(R.id.textv);



        sp=MainActivity.this.getSharedPreferences("com.example.alertdialog", Context.MODE_PRIVATE);
        String newlang= null;
        try {
            newlang = (String) ObjectSerializer.deserialize(sp.getString("language",ObjectSerializer.serialize(new String())));
        } catch (IOException e) {
            e.printStackTrace();
        }



        if(newlang=="") {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_btn_speak_now)
                    .setTitle("Which language do you speak?")
                    .setMessage("Select any language you want.")
                    .setPositiveButton("English", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setlang("English");
                        }
                    })
                    .setNegativeButton("Hindi", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setlang("Hindi");
                        }
                    })
                    .show();
        }
        else{
            textv.setText(newlang);
        }

    }
}
package com.example.database;

import static com.example.database.R.id.logIn;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener {
    private ListView lv;
    private ArrayList<String> myList;
    private ArrayAdapter<String> adapter;
    public static FrameLayout frLog,frAdd,frUp,frDash;
    private BottomNavigationView nav1;
    private update update;
    private Login login;
    private add add;
    private dash dash;
    public static boolean logedin=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        nav1 = findViewById(R.id.nav);
        frLog = findViewById(R.id.logIn);
        frAdd = findViewById(R.id.add);
        frUp = findViewById(R.id.up);
        frDash = findViewById(R.id.dash);


















        update=new update();
        login=new Login();
        add=new add();
        dash=new dash();

        getSupportFragmentManager().beginTransaction().replace(R.id.logIn,login).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.add,add).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.up,update).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.dash,dash).commit();
        frLog.setVisibility(View.VISIBLE);
        frUp.setVisibility(View.INVISIBLE);
        frAdd.setVisibility(View.INVISIBLE);
        frDash.setVisibility(View.INVISIBLE);

        nav1.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.logIn) {
                    if(logedin){
                    frLog.setVisibility(View.VISIBLE);
                    frUp.setVisibility(View.INVISIBLE);
                    frAdd.setVisibility(View.INVISIBLE);
                    frDash.setVisibility(View.INVISIBLE);}
                    else{
                        Toast.makeText(MainActivity.this, "Log In First", Toast.LENGTH_SHORT).show();
                    }

                }
                if (item.getItemId() == R.id.up) {
                    if(logedin){
                    frLog.setVisibility(View.INVISIBLE);
                    frUp.setVisibility(View.VISIBLE);
                    frAdd.setVisibility(View.INVISIBLE);
                    frDash.setVisibility(View.INVISIBLE);}
                    else{
                        Toast.makeText(MainActivity.this, "Log In First", Toast.LENGTH_SHORT).show();
                    }

                }
                if (item.getItemId() == R.id.add) {
                    if(logedin){
                    frLog.setVisibility(View.INVISIBLE);
                    frUp.setVisibility(View.INVISIBLE);
                    frAdd.setVisibility(View.VISIBLE);
                    frDash.setVisibility(View.INVISIBLE);}
                    else{
                        Toast.makeText(MainActivity.this, "Log In First", Toast.LENGTH_SHORT).show();
                    }

                }
                if (item.getItemId() == R.id.dash) {
                    if(logedin){
                    frLog.setVisibility(View.INVISIBLE);
                    frUp.setVisibility(View.INVISIBLE);
                    frAdd.setVisibility(View.INVISIBLE);
                    frDash.setVisibility(View.VISIBLE);}
                    else{
                        Toast.makeText(MainActivity.this, "Log In First", Toast.LENGTH_SHORT).show();
                    }


                }

                return true;
            }

        });


    }



    public void exit(View view) {
       System.exit(0);
    }


    public void add(View view) {
        frLog.setVisibility(View.INVISIBLE);
        frUp.setVisibility(View.INVISIBLE);
        frAdd.setVisibility(View.VISIBLE);
        frDash.setVisibility(View.INVISIBLE);
    }




    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
    public static void move(){
        frLog.setVisibility(View.INVISIBLE);
        frUp.setVisibility(View.VISIBLE);
        frAdd.setVisibility(View.INVISIBLE);
        frDash.setVisibility(View.INVISIBLE);
    }


}
package com.example.database;

import static android.content.Context.MODE_PRIVATE;

import static com.example.database.MainActivity.frDash;
import static com.example.database.MainActivity.frLog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends Fragment implements View.OnClickListener {
    Button log;
    Button sign;
    private EditText p,u;
    View view;
    Dialog e;
    Button exit;








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view= inflater.inflate(R.layout.login, container, false);
       log=view.findViewById(R.id.log);
       sign=view.findViewById(R.id.sign);
       p=view.findViewById(R.id.password);
       u=view.findViewById(R.id.username);
       log.setOnClickListener(this);
       sign.setOnClickListener(this);
       exit=view.findViewById(R.id.Exit);
       exit.setOnClickListener(this);

        e = new Dialog(requireContext());
        e.setContentView(R.layout.exit);








       return view;

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.Exit) {
            e.setCancelable(true);
            e.show();}

        if (view.getId() == R.id.sign) {
            String userName =u.getText().toString();
            String password =p.getText().toString();
            if(userName.isEmpty()|| password.isEmpty() )
                Toast.makeText(requireContext(), "fill all fields", Toast.LENGTH_SHORT).show();
            else{
                SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", userName);
                editor.putString("password", password);
                editor.apply();

                Toast.makeText(requireContext(), "Signup successful!", Toast.LENGTH_SHORT).show();

            }
        }
        if (view.getId() == R.id.log) {
            String userName =u.getText().toString();
            String password =p.getText().toString();
            if(userName.isEmpty()|| password.isEmpty() )
                Toast.makeText(requireContext(), "fill all fields", Toast.LENGTH_SHORT).show();
            else{
                SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                String savedUser = prefs.getString("username", "");
                String savedPass = prefs.getString("password", "");

                if (userName.equals(savedUser) && password.equals(savedPass)) {
                    Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                    MainActivity.logedin=true;
                    frDash.setVisibility(View.VISIBLE);
                    MainActivity.frLog.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(requireContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
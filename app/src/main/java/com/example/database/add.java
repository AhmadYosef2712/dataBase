package com.example.database;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class add extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add, container, false);

        Button saveButton = view.findViewById(R.id.save);
        EditText nameEditText = view.findViewById(R.id.name);
        EditText amountEditText = view.findViewById(R.id.amount);

        saveButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String a = amountEditText.getText().toString().trim();


            if (name.isEmpty()|| a.isEmpty() ) {

                Toast.makeText(requireContext(), "Please enter a name (String) and amount(Integer)", Toast.LENGTH_SHORT).show();
                return;
            }
            int amount = Integer.parseInt(a);

            Bundle bundle = new Bundle();
            bundle.putInt("amount", amount);
            bundle.putString("name", name);
            getParentFragmentManager().setFragmentResult("name", bundle);
            getParentFragmentManager().setFragmentResult("amount",bundle);

            nameEditText.setText("");
            amountEditText.setText(""); // Clear input
            Toast.makeText(requireContext(), "Client Added", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}

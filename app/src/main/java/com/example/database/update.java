package com.example.database;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class update extends Fragment {

    private EditText name, amount;
    private Button updateBtn;
    private long clientId = -1;

    public update() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update, container, false);
        name = view.findViewById(R.id.name);
        amount = view.findViewById(R.id.amount);
        updateBtn = view.findViewById(R.id.u);

        // read passed args (id, name, amount)
        if (getArguments() != null) {
            clientId = getArguments().getLong("id", -1);
            String n = getArguments().getString("name", "");
            int a = getArguments().getInt("amount", 0);
            name.setText(n);
            amount.setText(String.valueOf(a));
        }

        updateBtn.setOnClickListener(v -> {
            String n = name.getText() != null ? name.getText().toString().trim() : "";
            String aStr = amount.getText() != null ? amount.getText().toString().trim() : "";
            if (TextUtils.isEmpty(n) || TextUtils.isEmpty(aStr)) {
                Toast.makeText(requireContext(), "All fields required", Toast.LENGTH_SHORT).show();
                return;
            }
            int a;
            try {
                a = Integer.parseInt(aStr);
            } catch (NumberFormatException ex) {
                Toast.makeText(requireContext(), "Invalid number", Toast.LENGTH_SHORT).show();
                return;
            }
            if (clientId == -1) {
                Toast.makeText(requireContext(), "Invalid client", Toast.LENGTH_SHORT).show();
                return;
            }
            // ModelClient doesn't provide setName so recreate with same id
            ModelClient updated = new ModelClient(n, a, clientId);
            if (dash.dbHelper != null) {
                dash.dbHelper.update(updated);
                dash.refreshList();
                MainActivity.frUp.setVisibility(View.INVISIBLE);
                MainActivity.frDash.setVisibility(View.VISIBLE);
            }
            Toast.makeText(requireContext(), "Client updated", Toast.LENGTH_SHORT).show();
            // close update fragment
            getParentFragmentManager().popBackStack();
        });

        return view;
    }
}

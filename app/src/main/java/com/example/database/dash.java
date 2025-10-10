package com.example.database;

import static java.nio.file.Files.move;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.database.update; // make sure you have this fragment

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class dash extends Fragment implements View.OnClickListener {

    private Button btnDelete, btnUpdate, btnSort;
    public static FrameLayout frUp;
    private View view;
    private static ListView listView;
    public static ArrayAdapter<ModelClient> adapter;
    public static ArrayList<ModelClient> clients = new ArrayList<>();
    public static DBHelper dbHelper;
    private Dialog d;
    private Dialog e;

    private Button name, amount;

    private String pendingName = "";
    private int pendingAmount = 0;

    public dash() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.dash, container, false);

        e = new Dialog(requireContext());
        e.setContentView(R.layout.exit);
        d = new Dialog(requireContext());
        d.setContentView(R.layout.dialog);
        name = d.findViewById(R.id.name);
        amount = d.findViewById(R.id.amount);
        name.setOnClickListener(this);
        amount.setOnClickListener(this);


        frUp = view.findViewById(R.id.up);
        btnSort = view.findViewById(R.id.sort);
        btnSort.setOnClickListener(this);
        btnDelete = view.findViewById(R.id.Delete);
        btnUpdate = view.findViewById(R.id.update);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        dbHelper = new DBHelper(requireContext());

        listView = view.findViewById(R.id.lv);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        loadClients();

        // Add new client listener
        getParentFragmentManager().setFragmentResultListener("name", this, (key, result) -> {
            pendingName = result.getString("name", "");
        });

        getParentFragmentManager().setFragmentResultListener("amount", this, (key, result) -> {
            pendingAmount = result.getInt("amount", -1);
            if (!pendingName.isEmpty() && pendingAmount >= 0) {
                ModelClient newClient = new ModelClient(pendingName);
                newClient.setAmount(pendingAmount);
                dbHelper.insert(newClient);
                Toast.makeText(requireContext(), "Client added: " + pendingName, Toast.LENGTH_SHORT).show();
                refreshList();
                pendingName = "";
                pendingAmount = -1;
            }
        });

        return view;
    }

    private void loadClients() {
        clients.clear();
        clients.addAll(dbHelper.selectAll());
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_single_choice, clients);
        listView.setAdapter(adapter);
    }

    public static void refreshList() {
        clients.clear();
        clients.addAll(dbHelper.selectAll());
        adapter.notifyDataSetChanged();
        listView.clearChoices();
    }

    private void deleteSelectedClient() {
        int position = listView.getCheckedItemPosition();
        if (position != ListView.INVALID_POSITION) {
            ModelClient selected = clients.get(position);
            dbHelper.deleteById(selected.getId());
            Toast.makeText(requireContext(), selected.getName() + " deleted", Toast.LENGTH_SHORT).show();
            refreshList();
        } else {
            Toast.makeText(requireContext(), "No client selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openUpdateFragment() {
        int position = listView.getCheckedItemPosition();
        if (position != ListView.INVALID_POSITION) {
            ModelClient selected = clients.get(position);

            // Create a bundle to pass client info
            Bundle bundle = new Bundle();
            bundle.putLong("id", selected.getId());
            bundle.putString("name", selected.getName());
            bundle.putInt("amount", selected.getAmount());

            // Create and show the update fragment
            update updateFrag = new update();
            updateFrag.setArguments(bundle);


            MainActivity.move();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.up, updateFrag);
            transaction.addToBackStack(null);
            transaction.commit();

        } else {
            Toast.makeText(requireContext(), "Select a client to update", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Delete) {
            deleteSelectedClient();
        } else if (v.getId() == R.id.update) {
            openUpdateFragment();
        }
        if (v.getId() == R.id.sort) {
            d.setCancelable(true);
            d.show();
        }
        if (v.getId() == R.id.name) {
            if (clients.isEmpty()) {
                Toast.makeText(requireContext(), "No clients to sort", Toast.LENGTH_SHORT).show();
                return;
            }

            Collections.sort(clients, new Comparator<ModelClient>() {
                @Override
                public int compare(ModelClient o1, ModelClient o2) {
                    return o1.getName().compareToIgnoreCase(o2.getName()); // A → Z
                }
            });

            adapter.notifyDataSetChanged();
            Toast.makeText(requireContext(), "Sorted by name (A → Z)", Toast.LENGTH_SHORT).show();
        }





        if (v.getId() == R.id.amount) {
            if (clients.isEmpty()) {
                Toast.makeText(requireContext(), "No clients to sort", Toast.LENGTH_SHORT).show();
                return;
            }

            // Sort from highest amount to lowest
            Collections.sort(clients, new Comparator<ModelClient>() {
                @Override
                public int compare(ModelClient o1, ModelClient o2) {
                    return Integer.compare(o2.getAmount(), o1.getAmount()); // descending order
                }
            });

            adapter.notifyDataSetChanged();
            Toast.makeText(requireContext(), "Sorted by amount (High → Low)", Toast.LENGTH_SHORT).show();
        }


    }
    }


package com.example.database;

import static java.nio.file.Files.move;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Objects;

public class dash extends Fragment implements  {
    public static FrameLayout frUp;
    private update update;
    private int a=0;
    public static int id=0;
    private ArrayList<ModelClient> clients = new ArrayList<>();
    private ModelClient c;
    private String x="";
    private View view;
    private ArrayList<String> clientList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dash, container, false);
        frUp = view.findViewById(R.id.up);
        update=new update();


        listView = view.findViewById(R.id.lv);
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_single_choice, clientList);
        listView.setAdapter(adapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //clientList.set(a, "Client Name\nAmount");


        // Receive new clients from Add fragment
        getParentFragmentManager().setFragmentResultListener("name", this, (requestKey, result) -> {
            String name = result.getString("name");
            if (name != null && !name.trim().isEmpty()) {
                x=name;
                adapter.notifyDataSetChanged();
            }
        });
        getParentFragmentManager().setFragmentResultListener("amount", this, (requestKey, result) -> {
           int amount = result.getInt("amount");
            if (amount >=0) {
                clientList.add(x+"\n"+amount);
                c=new ModelClient(x,amount,id);
                clients.add(c);
                id++;
                adapter.notifyDataSetChanged();
            }
        });




        return view;
    }






    private void deleteSelectedClient() {
        int position = listView.getCheckedItemPosition();
        if (position != ListView.INVALID_POSITION) {
            String removedName = clientList.remove(position);
            adapter.notifyDataSetChanged();
            listView.clearChoices(); // Clear selection
            Toast.makeText(requireContext(), removedName + " deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "No client selected", Toast.LENGTH_SHORT).show();
        }
    }
}

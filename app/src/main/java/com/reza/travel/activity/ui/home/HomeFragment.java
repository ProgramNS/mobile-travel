package com.reza.travel.activity.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.reza.travel.R;
import com.reza.travel.activity.BookKeretaActivity;
import com.reza.travel.activity.HistoryActivity;
import com.reza.travel.activity.ProfileActivity;
import com.reza.travel.databinding.FragmentHomeBinding;
import com.reza.travel.session.SessionManager;

public class HomeFragment extends Fragment {

    private SessionManager session;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize SessionManager
        session = new SessionManager(getActivity().getApplicationContext());
        session.checkLogin(); // Check if the user is logged in


        // Handle menu actions for Profile, History, and Booking activities
        view.findViewById(R.id.cardProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ProfileActivity
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                startActivity(i);
            }
        });

        view.findViewById(R.id.cardHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to HistoryActivity
                Intent i = new Intent(getActivity(), HistoryActivity.class);
                startActivity(i);
            }
        });

        view.findViewById(R.id.cardBookKereta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to BookKeretaActivity
                Intent i = new Intent(getActivity(), BookKeretaActivity.class);
                startActivity(i);
            }
        });

        view.findViewById(R.id.cardBookHotel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a message indicating the feature is under development
                Toast.makeText(getActivity(), "Mohon maaf, sistem sedang dalam pengembangan.", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
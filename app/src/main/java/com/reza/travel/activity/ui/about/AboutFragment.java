package com.reza.travel.activity.ui.about;

import android.content.Intent;
import android.graphics.text.LineBreaker;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.reza.travel.R;
import com.reza.travel.activity.BookKeretaActivity;
import com.reza.travel.activity.KatalogActivity;
import com.reza.travel.databinding.FragmentAboutBinding;

public class AboutFragment extends Fragment {

    private FragmentAboutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AboutViewModel aboutViewModel =
                new ViewModelProvider(this).get(AboutViewModel.class);

        binding = FragmentAboutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Setup Toolbar
        Toolbar toolbar = root.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        if (((AppCompatActivity) requireActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("About");
        }
            toolbar.setNavigationOnClickListener(v -> {
            requireActivity().onBackPressed();
            });


        // Bind ViewModel text to TextView
        final TextView textView = binding.textHome;
        aboutViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            textView.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


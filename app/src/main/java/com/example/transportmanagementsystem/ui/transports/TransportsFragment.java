package com.example.transportmanagementsystem.ui.transports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.transportmanagementsystem.databinding.FragmentTransportsBinding;

public class TransportsFragment extends Fragment {

    private FragmentTransportsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TransportsViewModel transportsViewModel =
                new ViewModelProvider(this).get(TransportsViewModel.class);

        binding = FragmentTransportsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
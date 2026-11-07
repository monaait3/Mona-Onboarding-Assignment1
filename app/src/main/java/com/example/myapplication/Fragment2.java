package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment2Binding;

public class Fragment2 extends Fragment {

    private Fragment2Binding binding;
    private OnContinueListener listener;

    public interface OnContinueListener {
        void onContinueFromFragment2(String name);
    }

    public Fragment2() {
        // Required empty public constructor
    }

    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();

        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnContinueListener) {
            listener = (OnContinueListener) context;
        } else {
            throw new IllegalStateException(
                    "Host Activity must implement OnContinueListener"
            );
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        binding = Fragment2Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void sendNameToActivity() {
        if (binding == null || listener == null) {
            return;
        }

        String name = binding.editText.getText().toString().trim();

        if (name.isEmpty()) {
            binding.editText.setError("Enter name!");
            binding.editText.requestFocus();
            return;
        }

        listener.onContinueFromFragment2(name);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment3Binding;

public class Fragment3 extends Fragment {

    private static final String ARG_NAME = "user_name";

    private Fragment3Binding binding;
    private OnConfirmationChangedListener listener;

    public interface OnConfirmationChangedListener {
        void onConfirmationChanged(boolean isChecked);
    }

    public Fragment3() {
        // Required empty public constructor
    }

    public static Fragment3 newInstance(String name) {
        Fragment3 fragment = new Fragment3();

        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnConfirmationChangedListener) {
            listener = (OnConfirmationChangedListener) context;
        } else {
            throw new IllegalStateException(
                    "Host Activity must implement OnConfirmationChangedListener"
            );
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        binding = Fragment3Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        String name = "";

        if (getArguments() != null) {
            name = getArguments().getString(ARG_NAME, "");
        }

        binding.textViewName.setText("Welcome, " + name);
        updateContinueButton(false);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        binding.checkBoxConfirm.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    if (listener != null) {
                        listener.onConfirmationChanged(isChecked);
                    }
                }
        );

        if (listener != null) {
            listener.onConfirmationChanged(
                    binding.checkBoxConfirm.isChecked()
            );
        }
    }

    public void updateContinueButton(boolean isChecked) {
        if (binding == null) {
            return;
        }

        binding.buttonContinue3.setEnabled(isChecked);

        if (isChecked) {
            binding.buttonContinue3.setText("Finish");
        } else {
            binding.buttonContinue3.setText("Continue");
        }
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
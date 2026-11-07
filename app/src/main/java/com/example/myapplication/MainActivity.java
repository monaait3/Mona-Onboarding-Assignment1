package com.example.myapplication;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
        implements Fragment2.OnContinueListener,
        Fragment3.OnConfirmationChangedListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            Fragment1 fragment1 = Fragment1.newInstance(null, null);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment1)
                    .commit();
        }

        getSupportFragmentManager()
                .addOnBackStackChangedListener(
                        this::updateActivityButtonVisibility
                );

        binding.buttonContinue.setOnClickListener(view -> {
            Fragment currentFragment = getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_container);

            if (currentFragment instanceof Fragment1) {
                Fragment2 fragment2 = Fragment2.newInstance(null, null);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment2)
                        .addToBackStack(null)
                        .commit();

            } else if (currentFragment instanceof Fragment2) {
                Fragment2 fragment2 = (Fragment2) currentFragment;
                fragment2.sendNameToActivity();
            }
        });
    }

    @Override
    public void onContinueFromFragment2(String name) {
        Fragment3 fragment3 = Fragment3.newInstance(name);

        binding.buttonContinue.setVisibility(View.GONE);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment3)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onConfirmationChanged(boolean isChecked) {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (currentFragment instanceof Fragment3) {
            Fragment3 fragment3 = (Fragment3) currentFragment;
            fragment3.updateContinueButton(isChecked);
        }
    }

    private void updateActivityButtonVisibility() {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (currentFragment instanceof Fragment3) {
            binding.buttonContinue.setVisibility(View.GONE);
        } else {
            binding.buttonContinue.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        updateActivityButtonVisibility();
    }
}
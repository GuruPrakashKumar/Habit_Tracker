package com.example.habbittrackerapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    private static final int REQUEST_CODE_GALLERY = 1;
    private ImageView profilePhotoImageView;
    private ActivityResultLauncher<String> galleryLauncher;
    private RecyclerView recyclerView;
    private ProfileAdapter profileAdapter;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Handle arguments if needed
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profilePhotoImageView = view.findViewById(R.id.profilePhotoImageView);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedProfileName = sharedPreferences.getString("profileName", "Save Your Name");
        TextView profileFragProfileName = view.findViewById(R.id.profileFragProfileName);
        profileFragProfileName.setText("Hey "+storedProfileName+" !");




        recyclerView = view.findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        profileAdapter = new ProfileAdapter(getOptionList(), getActivity());
        recyclerView.setAdapter(profileAdapter);

        //allows the user to select a new photo and set it in the profile picture.
        profilePhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalleryForPhotoSelection();
            }
        });


        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {

                            profilePhotoImageView.setImageURI(result);
                        }
                    }
                });

        return view;
    }

    private List<Option> getOptionList() {
        List<Option> optionList = new ArrayList<>();
        optionList.add(new Option("My Account Info", R.drawable.baseline_account_circle_24, MyAccountInfoActivity.class));
        optionList.add(new Option("My Subscription Info", R.drawable.baseline_subscription_24, MySubscriptionInfoActivity.class));
        optionList.add(new Option("About This App", R.drawable.baseline_about_this_app_24, AboutAppActivity.class));

        return optionList;
    }


    private void openGalleryForPhotoSelection() {
        galleryLauncher.launch("image/*");
    }
}


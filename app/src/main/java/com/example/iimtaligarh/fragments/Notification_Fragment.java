package com.example.iimtaligarh.fragments;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.iimtaligarh.R;
import com.example.iimtaligarh.adapter.Recylar_Notification_Adapter;
import com.example.iimtaligarh.databinding.FragmentNotificationBinding;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.ArrayList;

public class Notification_Fragment extends Fragment {

    private FragmentNotificationBinding binding;
    ArrayList<String> notification_text = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().getWindow().setStatusBarColor(ContextCompat
                .getColor(requireContext(), R.color.white));
        binding = FragmentNotificationBinding.inflate(getLayoutInflater(), container, false);

        binding.recyclarView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseMessaging.getInstance().subscribeToTopic("your_topic");

        Recylar_Notification_Adapter recylar_notification_adapter =
                new Recylar_Notification_Adapter(getContext(), notification_text);

        data();

        binding.recyclarView.setAdapter(recylar_notification_adapter);

        if (recylar_notification_adapter.getItemCount() == 0)
            binding.notificationText.setVisibility(View.VISIBLE);
        else
            binding.notificationText.setVisibility(View.INVISIBLE);

        return binding.getRoot();
    }

    void data(){
        notification_text.add("Results of Bca (1st, 3rd & 5th) Semester will be declared on 12-Aug-2023.");
        notification_text.add("This is to inform you that a holiday has been declared on 15 August 2023 due to independence day. We wish everyone a happy 77th independence day.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
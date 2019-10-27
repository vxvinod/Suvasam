package com.example.suvasam;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suvasam.model.Donate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DonateConfirmationFragment extends DialogFragment {

    HashMap<String, Donate> mDonateList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate_confirmation, container, false);
        final LinearLayout summaryLayout = view.findViewById(R.id.confSummaryDetails);
        Button checkoutBtn = view.findViewById(R.id.checkoutBtn);
        Button cancel = view.findViewById(R.id.confirmCancel);
        if(savedInstanceState == null) {
            mDonateList = (HashMap<String, Donate>) getArguments().getSerializable("selectedAreaList");
        } else {
            mDonateList = (HashMap<String, Donate>) savedInstanceState.getSerializable("areaList");
        }
        Log.e("Dialog Conf Frag", "Inside Create View"+ mDonateList.size());
        for(HashMap.Entry<String, Donate>  plant : mDonateList.entrySet()) {
            Log.e("Dialog Conf Frag", "Inside Create View For Loop");
            Log.e("Dialog Conf Frag", "plant key"+plant.getKey());
            Log.e("Dialog Conf Frag", "plant class"+plant.getClass());
            Log.e("Dialog Conf Frag", "plant"+plant.getValue());
            TextView ch = new TextView(getContext());
            Log.e("Dialog frag", plant.getValue().getName()+" " + plant.getValue().getPlantsCount() + " "+ plant.getValue().getDonationAmt());
            ch.setText(plant.getValue().getName() + " " + plant.getValue().getPlantsCount()+ " "+ plant.getValue().getDonationAmt());
            summaryLayout.addView(ch);
        }

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Toast.makeText(getContext(), "Payment Done", Toast.LENGTH_LONG).show();
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference ref = db.getReference();
                for(Map.Entry<String, Donate> area : mDonateList.entrySet()) {
                    ref.child("area").child(String.valueOf(area.getValue().getId())).child("donated").setValue("yes");
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState !=null) {
            mDonateList = (HashMap<String, Donate>) savedInstanceState.getSerializable("areaList");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("areaList", mDonateList);
    }

}

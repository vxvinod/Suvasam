package com.example.suvasam;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.suvasam.database.DonateFirebase;
import com.example.suvasam.model.Donate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class DonateDialogFragment extends DialogFragment {
    public DonateDialogFragment() {
    }

    HashMap<String, Donate> mDonateList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDonateList = new HashMap<>();
        View view = inflater.inflate(R.layout.fragment_donate_dialog, container, false);
        final LinearLayout checkBoxLayout = view.findViewById(R.id.checkBoxCollection);
        Button add = view.findViewById(R.id.btnAdd);
        Button cancel = view.findViewById(R.id.donateCancel);
        Button reset = view.findViewById(R.id.reset);
        if(savedInstanceState == null) {
            mDonateList = (HashMap<String, Donate>) getArguments().getSerializable("areaList");
        } else {
            mDonateList = (HashMap<String, Donate>) savedInstanceState.getSerializable("areaList");
        }

         Log.e("Dialog Frag", "Inside Create View"+ mDonateList.size());
        for(Map.Entry<String, Donate> plant : mDonateList.entrySet()) {
            Log.e("Dialog Frag", "Inside Create View For Loop");
            if(plant.getValue().getDonated().equals("no")) {
                CheckBox ch = new CheckBox(getContext());
                Log.e("Dialog frag", plant.getValue().getName() + " " + plant.getValue().getPlantsCount() + " " + plant.getValue().getDonationAmt());
                ch.setText(plant.getValue().getName() + " " + plant.getValue().getPlantsCount() + " plants, costs " + plant.getValue().getDonationAmt()+ "rupees");
                ch.setId(plant.getValue().getId());
                checkBoxLayout.addView(ch);
            }
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Donate> selectedArea = new HashMap<>();
                int totalDonationAmt = 0;
                for(int i = 0; i < checkBoxLayout.getChildCount(); i++) {
                    View nextChild = checkBoxLayout.getChildAt(i);
                    if(nextChild instanceof CheckBox) {
                        CheckBox check = (CheckBox) nextChild;
                        if (check.isChecked()) {
                            //HashMap<String, Donate> donateDetails = getDonationDetails(mDonateList, check);
                            selectedArea.put(String.valueOf(mDonateList.get(String.valueOf(check.getId())).getId()), mDonateList.get(String.valueOf(check.getId())));
                            totalDonationAmt = totalDonationAmt + mDonateList.get(String.valueOf(check.getId())).getDonationAmt();
                        }
                    }
                }

                DonateConfirmationFragment confirmationFragment = new DonateConfirmationFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedAreaList", selectedArea);
                confirmationFragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dismiss();
                ft.replace(R.id.donateFrameLayout, confirmationFragment);
                ft.addToBackStack(null);
                ft.commit();
                checkBoxLayout.removeAllViews();
                Toast.makeText(getContext(), String.valueOf(totalDonationAmt), Toast.LENGTH_LONG ).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Toast.makeText(getContext(), "Payment Done", Toast.LENGTH_LONG).show();
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference ref = db.getReference();
                for(HashMap.Entry<String, Donate> area : mDonateList.entrySet()) {
                    ref.child("area").child(String.valueOf(area.getValue().getId())).child("donated").setValue("no");
                }

            }
        });


        return view;
    }

    public HashMap<String, Donate> getDonationDetails(HashMap<String, Donate> donateList, CheckBox checkBox) {
        HashMap<String, Donate> donateInfo = new HashMap<>();
        for(HashMap.Entry<String, Donate> donateDetails: donateList.entrySet()) {
            if(donateDetails.getValue().getId() == (checkBox.getId())){
                donateInfo.put(String.valueOf(donateDetails.getValue().getId()), donateDetails.getValue());
                return donateInfo;
            }
        }
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
            mDonateList = (HashMap<String, Donate>) savedInstanceState.getSerializable("areaList");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("areaList", mDonateList);
    }
}

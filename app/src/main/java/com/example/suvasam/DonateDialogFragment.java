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

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

public class DonateDialogFragment extends DialogFragment {
    public DonateDialogFragment() {
    }

    ArrayList<Donate> mDonateList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDonateList = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_donate_dialog, container, false);
        final LinearLayout checkBoxLayout = view.findViewById(R.id.checkBoxCollection);
        Button add = view.findViewById(R.id.btnAdd);
        if(savedInstanceState == null) {
            mDonateList = getArguments().getParcelableArrayList("areaList");
        } else {
            mDonateList = savedInstanceState.getParcelableArrayList("areaList");
        }

         Log.e("Dialog Frag", "Inside Create View"+ mDonateList.size());
        for(Donate plant : mDonateList) {
            Log.e("Dialog Frag", "Inside Create View For Loop");
            if(plant.getDonated().equals("no")) {
                CheckBox ch = new CheckBox(getContext());
                Log.e("Dialog frag", plant.getName() + " " + plant.getPlantsCount() + " " + plant.getDonationAmt());
                ch.setText(plant.getName() + " " + plant.getPlantsCount() + " " + plant.getDonationAmt());
                ch.setId(plant.getId());
                checkBoxLayout.addView(ch);
            }
        }
//        for(int i = 0; i < 5; i++) {
//            CheckBox ch = new CheckBox(getContext());
//            ch.setText("I am Dynamic");
//            checkBoxLayout.addView(ch);
//        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Donate> selectedArea = new ArrayList<>();
                int totalDonationAmt = 0;
                for(int i = 0; i < checkBoxLayout.getChildCount(); i++) {
                    View nextChild = checkBoxLayout.getChildAt(i);
                    if(nextChild instanceof CheckBox) {
                        CheckBox check = (CheckBox) nextChild;
                        if (check.isChecked()) {
                            selectedArea.add(mDonateList.get(check.getId()));
                            totalDonationAmt = totalDonationAmt + mDonateList.get(i).getDonationAmt();
                        }
                    }
                }

                DonateConfirmationFragment confirmationFragment = new DonateConfirmationFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("selectedAreaList", selectedArea);
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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
            mDonateList = savedInstanceState.getParcelableArrayList("areaList");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("areaList", mDonateList);
    }
}

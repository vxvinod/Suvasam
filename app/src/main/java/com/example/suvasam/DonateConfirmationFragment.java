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
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DonateConfirmationFragment extends DialogFragment {

    ArrayList<Donate> mDonateList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate_confirmation, container, false);
        final LinearLayout summaryLayout = view.findViewById(R.id.confSummaryDetails);
        Button checkoutBtn = view.findViewById(R.id.checkoutBtn);
        mDonateList = getArguments().getParcelableArrayList("selectedAreaList");
        Log.e("Dialog Conf Frag", "Inside Create View"+ mDonateList.size());
        for(Donate plant : mDonateList) {
            Log.e("Dialog Conf Frag", "Inside Create View For Loop");
            TextView ch = new TextView(getContext());
            Log.e("Dialog frag", plant.getName()+" " + plant.getPlantsCount() + " "+ plant.getDonationAmt());
            ch.setText(plant.getName() + " " + plant.getPlantsCount()+ " "+ plant.getDonationAmt());
            summaryLayout.addView(ch);
        }

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Toast.makeText(getContext(), "Payment Done", Toast.LENGTH_LONG).show();
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference ref = db.getReference();
                for(Donate area : mDonateList) {
                    ref.child("area").child(String.valueOf(area.getId())).child("donated").setValue("yes");
                }

            }
        });

        return view;

    }

}

package com.example.suvasam;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suvasam.adapter.EventListAdapter;
import com.example.suvasam.database.EventFirebase;
import com.example.suvasam.model.Events;
import com.example.suvasam.widgets.SuvasamEventWidget;
import com.example.suvasam.widgets.WidgetUpdateIntentService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Context mContext;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private EventListAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    ArrayList<Events> mEventsList = new ArrayList<>();
    public EventsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventsFragment newInstance(String param1, String param2) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("EVENT FRAG", "Inside if ONCREATE");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        if(savedInstanceState == null) {
            Log.e("EVENT FRAG", "Inside if onViewCreated");
            //int id, String name, String imageUrl, String description, String date, Boolean fav
            mEventsList.add(new Events(5, "name", "tttt", "tttt", "ttt", true));
            mEventsList.add(new Events(6, "name", "tttt", "tttt", "ttt", true));

            fetchDataFromFirebase();
        } else {
            Log.e("EVENT FRAG", "Inside else onViewCreated");
            mEventsList = savedInstanceState.getParcelableArrayList("eventList");
        }

//        mRecyclerView = view.findViewById(R.id.recyclerview);
//        if(savedInstanceState == null) {
//            Log.e("EVENT FRAG", "Inside if ON CREATE VIEW");
//            fetchDataFromFirebase();
//        } else {
//            Log.e("EVENT FRAG", "Inside else ON CREATE VIEW");
//            mEventsList = savedInstanceState.getParcelableArrayList("eventList");
//        }
//
//
//        mAdapter = new EventListAdapter(getContext(), mEventsList);
//        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e("EVENT FRAG", "Inside onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerview);

        if(savedInstanceState != null) {
            mAdapter = new EventListAdapter(getContext(), mEventsList);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        // Inflate the layout for this fragment

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        mContext = context;
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//
//        mListener = null;
//    }
    public void fetchDataFromFirebase() {
        final ArrayList<Events> eventsList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        ref.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("Count", ""+dataSnapshot.getChildrenCount());
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Events events = postSnapshot.getValue(Events.class);
                    mEventsList.add(events);
                    Log.e("Get Data", events.name);
                }
                mAdapter = new EventListAdapter(getContext(), mEventsList);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                Log.e("SETTING WIDGET", "Setting Data to Widget");
                SuvasamEventWidget.setEvents(mEventsList);
                WidgetUpdateIntentService.startAddWidgetData( getContext(), mEventsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
            mEventsList = savedInstanceState.getParcelableArrayList("eventList");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.e("EVENT FRAG", "Inside onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("eventList", mEventsList);
    }
}

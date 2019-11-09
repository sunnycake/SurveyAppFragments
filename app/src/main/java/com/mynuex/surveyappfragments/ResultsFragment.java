package com.mynuex.surveyappfragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.ContentValues.TAG;


public class ResultsFragment extends Fragment {

    private static final String ARG_YES_COUNT = "arg_yes";
    private static final String ARG_NO_COUNT = "arg_no";

    private int mYesCount = 0;
    private int mNoCount = 0;

    private TextView yesView;
    private TextView noView;
    private TextView newOptionOne;
    private TextView newOptionTwo;
    private Button mResetButton;
    // interface for hosting activity to implement for vote reset results
    public interface ResetListener {
        void resetResults(int resetYes, int resetNo);
    }
    // instance of interface
    private ResetListener mResetListener;

    public ResultsFragment() {
        // Required empty public constructor
    }

    public static ResultsFragment newInstance(int yes, int no) {
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_YES_COUNT, yes);
        args.putInt(ARG_NO_COUNT, no);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mYesCount = getArguments().getInt(ARG_YES_COUNT);
            mNoCount = getArguments().getInt(ARG_NO_COUNT);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d(TAG, "onAttach");

        // verify listener
        if (context instanceof ResultsFragment.ResetListener) {
            mResetListener = (ResultsFragment.ResetListener) context;
            Log.d(TAG, "on attach results reset Listener set");
        } else {
            throw new RuntimeException(context.toString() + " must implement ResetListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        yesView = view.findViewById(R.id.yes_count_view);
        noView = view.findViewById(R.id.no_count_view);

        newOptionOne = view.findViewById(R.id.optionOneTV);
        newOptionTwo = view.findViewById(R.id.optionTwoTV);

        mResetButton = view.findViewById(R.id.reset_button);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mYesCount = 0; // resetting votes back to zero
                mNoCount = 0;
                //then updating the yes & no TextView to display reset
                yesView.setText(String.valueOf(mYesCount));
                noView.setText(String.valueOf(mNoCount));
                // send reset votes to hosting activity via listener
                mResetListener.resetResults(mYesCount, mNoCount);
            }
        });

        return view;
    }
    // method was received from hosting activity that held Survey Fragment's data to be pass on to Results Fragment.
    public void setVotes(int yes, int no) {
        // setting initial survey yes & no counts
        yesView.setText(String.valueOf(yes));
        noView.setText(String.valueOf(no));
    }
    public void newOptions(String optionOne, String optionTwo) {
        newOptionOne.setText(String.valueOf(optionOne));
        newOptionTwo.setText(String.valueOf(optionTwo));
    }
}
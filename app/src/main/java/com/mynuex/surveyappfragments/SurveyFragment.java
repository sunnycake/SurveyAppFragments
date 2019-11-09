package com.mynuex.surveyappfragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyFragment extends Fragment {

    private int mYesCount = 0;
    private int mNoCount = 0;

    private TextView mQuestion;
    private Button mEditButton;
    private Button mNoButton;
    private Button mYesButton;
    // interface that hosting activity has to implement
    public interface SurveyVoteListener {
        void voteListener(int yes, int no);
        void editListener(String newQuestion, String newOptionOne, String newOptionTwo);
    }

    private SurveyVoteListener mVoteListener;

    public SurveyFragment() {
        // Required empty public constructor
    }

    public static SurveyFragment newInstance() {
        return new SurveyFragment();

    }
    // verify listener has implemented listener else, throws exceptions.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d(TAG, "onAttach");

        if(context instanceof SurveyVoteListener){
            mVoteListener = (SurveyVoteListener) context;
            Log.d(TAG, "On attach survey vote listener set " + mVoteListener);
        } else {
            throw new RuntimeException(context.toString() + " must implement SurveyVoteListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_survey, container, false);

        mQuestion = view.findViewById(R.id.survey_question_textView);
        mEditButton = view.findViewById(R.id.edit_button);
        mNoButton = view.findViewById(R.id.no_button);
        mYesButton = view.findViewById(R.id.yes_button);
        // click listener for votes then sends to hosting activity
        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mYesCount++;
                // this sends data to hosting activity
                mVoteListener.voteListener(mYesCount, mNoCount);
            }
        });

        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNoCount++;
                mVoteListener.voteListener(mYesCount, mNoCount);
            }
        });

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hosting activity has to implement .editListener method
                // in order to edit survey question
                mVoteListener.editListener("newQuestion",
                        "newOptionOne", "newOptionTwo");
            }
        });

        return view;
    }
    // method invoked from hosting activity to reset votes
    public void resetVotes(int resetYes, int resetNo) {
        mYesCount = resetYes;
        mNoCount = resetNo;
    }

    public void newSurvey(String question, String optionOne, String optionTwo) {
        mQuestion.setText(question);
        mYesButton.setText(optionOne);
        mNoButton.setText(optionTwo);
    }

    public void setSurveyVoteListener(SurveyVoteListener mVoteListener) {
        this.mVoteListener = mVoteListener;
    }

}

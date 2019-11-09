package com.mynuex.surveyappfragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigurationFragment extends Fragment {

    private final static String ARG_NEW_QUESTION = "new question argument";
    private final static String ARG_OPTION_ONE = "option one argument";
    private final static String ARG_OPTION_TWO = "option two argument";

    String editQuestion;
    String editOptionOne;
    String editOptionTwo;

    private EditText mNewQuestion;
    private EditText mOptionOne;
    private EditText mOptionTwo;
    private Button mSaveButton;

    // new instance from hosting activity. Setting the arguments to receive new data
    public static ConfigurationFragment newInstance(String newQuestion, String newOptionOne, String newOptionTwo) {
        ConfigurationFragment fragment = new ConfigurationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NEW_QUESTION, newQuestion);
        args.putString(ARG_OPTION_ONE, newOptionOne);
        args.putString(ARG_OPTION_TWO, newOptionTwo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            editQuestion = getArguments().getString(ARG_NEW_QUESTION);
            editOptionOne = getArguments().getString(ARG_OPTION_ONE);
            editOptionTwo = getArguments().getString(ARG_OPTION_TWO);
        }
    }

    public interface EditQuestionListener {
        void editQuestion(String question, String optionOne, String optionTwo );
    }

    private EditQuestionListener mEditQuestionListener;

    public ConfigurationFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");

        // verify listener
        if (context instanceof ConfigurationFragment.EditQuestionListener) {
            mEditQuestionListener = (ConfigurationFragment.EditQuestionListener) context;
            Log.d(TAG, "on attach results configuration Listener set");
        } else {
            throw new RuntimeException(context.toString() + " must implement EditQuestionListener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_configuration, container, false);

        mNewQuestion = view.findViewById(R.id.new_question_editText);
        mOptionOne = view.findViewById(R.id.option_one);
        mOptionTwo = view.findViewById(R.id.option_two);
        mSaveButton = view.findViewById(R.id.save_new_question);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getArgNewQuestion = mNewQuestion.getText().toString();
                String getArgOptionOne = mOptionOne.getText().toString();
                String getArgOptionTwo = mOptionTwo.getText().toString();
                // listener to send to hosting activity with new survey data
                mEditQuestionListener.editQuestion(getArgNewQuestion, getArgOptionOne, getArgOptionTwo);

            }
        });
        return view;
    }
}



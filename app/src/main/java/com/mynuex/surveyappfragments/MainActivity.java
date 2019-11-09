package com.mynuex.surveyappfragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements SurveyFragment.SurveyVoteListener,
        ResultsFragment.ResetListener, ConfigurationFragment.EditQuestionListener {

    private static final String TAG_SURVEY_FRAG = "SURVEY FRAGMENT";
    private static final String TAG_RESULTS_FRAG = "RESULTS FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adding fragments to hosting activity
        SurveyFragment surveyFragment = SurveyFragment.newInstance();
        surveyFragment.setSurveyVoteListener(this);

        ResultsFragment resultsFragment = ResultsFragment.newInstance(0, 0);

        // Fragment manager to add, remove, or replace a fragment and transaction
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // add, remove, or replace fragments
        ft.add(R.id.survey_container, surveyFragment, TAG_SURVEY_FRAG);
        ft.add(R.id.results_container, resultsFragment, TAG_RESULTS_FRAG);

        // Commit to display the fragment transactions to the container
        ft.commit();

    }
    // implement received from Survey Fragment. Data can now be passed on to another fragment
    @Override
    public void voteListener(int yes, int no) {
        FragmentManager fm = getSupportFragmentManager();
        // getting fragment that's receiving the data by using findFragmentById
        ResultsFragment resultsFragment = (ResultsFragment) fm.findFragmentById(R.id.results_container);
        // receiving fragment will use setVotes public method to set data
        resultsFragment.setVotes(yes, no); //

    }
    // implement received from Results Fragment.
    @Override
    public void resetResults(int resetYes, int resetNo) {
        FragmentManager fm = getSupportFragmentManager();
        SurveyFragment surveyFragment = (SurveyFragment) fm.findFragmentById(R.id.survey_container);
        // Survey Fragment has to invoke resetVotes method to reset votes
        surveyFragment.resetVotes(resetYes, resetNo);

    }
    // implement received from Survey Fragment to edit survey.
    @Override
    public void editListener(String newQuestion, String newOptionOne, String newOptionTwo) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // creating new instance in Configuration Fragment to edit new survey
        ConfigurationFragment cf = ConfigurationFragment.newInstance("newQuestion", "newOptionOne", "newOptionTwo");
        // replacing hosting activity with Configuration Fragment
        ft.replace(R.id.container, cf);
        // add transaction to back stack
        ft.addToBackStack(null);
        ft.commit();
    }

    // implement received from Configuration Fragment with new survey data
    @Override
    public void editQuestion(String question, String optionOne, String optionTwo) {
        FragmentManager fm = getSupportFragmentManager();
        ResultsFragment resultsFragment = (ResultsFragment) fm.findFragmentById(R.id.results_container);
        SurveyFragment surveyFragment = (SurveyFragment) fm.findFragmentById(R.id.survey_container);
        // updating Survey Fragment with new data
        surveyFragment.newSurvey(question, optionOne, optionTwo);
        // updating Results Fragment optionOneTV and optionTwoTV
        resultsFragment.newOptions(optionOne, optionTwo);

    }
}
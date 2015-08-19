package org.md2k.moodsurfing;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.md2k.utilities.Report.Log;
import org.md2k.datakitapi.time.DateTime;

import java.util.ArrayList;
import java.util.Random;


/**
 * Copyright (c) 2015, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 */
public class FragmentChoiceSelectImage extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";
    public static final String ARG_TYPE = "type";
    private static final String TAG = FragmentChoiceSelectImage.class.getSimpleName();

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    private int mExerciseType;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static FragmentChoiceSelectImage create(int exerciseType, int pageNumber) {
        FragmentChoiceSelectImage fragment = new FragmentChoiceSelectImage();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putInt(ARG_TYPE, exerciseType);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentChoiceSelectImage() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        mExerciseType = getArguments().getInt(ARG_TYPE);
    }

    void setQuestionText(ViewGroup rootView, Question question) {
        String question_text = question.getQuestion_text();
        if (question_text.contains("_____")) {
            String replace=Questions.getInstance().getQuestion(Constants.NOTICE_AND_ACCEPT, 1).getQuestion_responses_selected_random();
            if(replace.equals("Other physical sensations")) {
                replace="physical sensations";
                while(question_text.contains("Is it changing or does it"))
                    question_text=question_text.replace("Is it changing or does it","Are they changing or do they");
                while(question_text.contains("Is"))
                    question_text=question_text.replace("Is","Are");
            }
            Log.d(TAG,""+Questions.getInstance().getQuestion(Constants.NOTICE_AND_ACCEPT,1).getQuestion_responses_selected()+" "+
            Questions.getInstance().getQuestion(Constants.NOTICE_AND_ACCEPT,1).getQuestion_responses_selected_random());
            question_text = question_text.replace("_____", "\""+replace+"\"");
        }
        ((TextView) rootView.findViewById(R.id.textViewDescription)).setText(question_text);
    }

    void setLinearLayout(ViewGroup rootView, Question question) {
        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.layout_horizontal_multiple_choice);
        if (question.getQuestion_type() != null && question.getQuestion_responses().size() > 2)
            ll.setOrientation(LinearLayout.VERTICAL);
        else ll.setOrientation(LinearLayout.HORIZONTAL);
    }

    void setTypeMultipleChoiceSelect(ViewGroup rootView, Question question) {
        Log.d(TAG,"setTypeMultipleChoiceSelect() question="+question.getQuestion_id()+" "+question.getQuestion_responses().size());
        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.layout_horizontal_multiple_choice);
        ArrayList<ToggleButton> toggleButtons = new ArrayList<>();
        CompoundButton.OnCheckedChangeListener listener;
        for (int i = 0; i < question.getQuestion_responses().size(); i++) {
            ToggleButton toggleButton = addToggleButtons(question, i);
            if (question.isType(Questions.MULTIPLE_CHOICE))
                listener = setOnCheckedListenerMultipleChoice(question,toggleButtons);
            else listener = setOnCheckedListenerMultipleSelect(question,toggleButtons);
            toggleButton.setOnCheckedChangeListener(listener);
            toggleButtons.add(toggleButton);
            if (i != 0) ll.addView(addView());
            ll.addView(toggleButton);
        }
    }
    CompoundButton.OnCheckedChangeListener setOnCheckedListenerMultipleSelect(final Question question, final ArrayList<ToggleButton> toggleButtons) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == false)
                    question.getQuestion_responses_selected().remove(buttonView.getText().toString());
                else if(buttonView.getText().equals("None of the above")){
                    for (int i = 0; i < toggleButtons.size(); i++) {
                        if (!toggleButtons.get(i).getText().equals(buttonView.getText()))
                            toggleButtons.get(i).setChecked(false);
                    }
                    question.getQuestion_responses_selected().clear();
                    question.getQuestion_responses_selected().add(buttonView.getText().toString());
                }
                else{
                    for (int i = 0; i < toggleButtons.size(); i++)
                        if (toggleButtons.get(i).getText().equals("None of the above")) {
                            toggleButtons.get(i).setChecked(false);
                            question.getQuestion_responses_selected().remove(toggleButtons.get(i).getText().toString());
                        }
                    question.getQuestion_responses_selected().add(buttonView.getText().toString());
                }
                question.setQuestion_responses_selected_random("");
                Log.d(TAG,"before random");
                if(question.getQuestion_id()==1 && question.getQuestion_responses_selected()!=null && question.getQuestion_responses_selected().size()!=0 && !question.getQuestion_responses_selected().get(0).equals("None of the above")){
                    Log.d(TAG,"inside random()");
                    Random rand = new Random();
                    int k = rand.nextInt(question.getQuestion_responses_selected().size());
                    question.setQuestion_responses_selected_random(question.getQuestion_responses_selected().get(k));
                    Log.d(TAG,"random k="+k+" text="+question.getQuestion_responses_selected().get(k)+" set="+question.getQuestion_responses_selected_random());
                }
            }
        };
    }

    CompoundButton.OnCheckedChangeListener setOnCheckedListenerMultipleChoice(final Question question, final ArrayList<ToggleButton> toggleButtons) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                    question.getQuestion_responses_selected().remove(buttonView.getText().toString());
                else{
                    for (int i = 0; i < toggleButtons.size(); i++) {
                        if (!toggleButtons.get(i).getText().equals(buttonView.getText()))
                            toggleButtons.get(i).setChecked(false);
                    }
                    question.getQuestion_responses_selected().clear();
                    question.getQuestion_responses_selected().add(buttonView.getText().toString());
                }
            }
        };
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView() mPageNumber="+mPageNumber);
        final ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_choice_select_image, container, false);
        Question question = Questions.getInstance().getQuestion(mExerciseType, mPageNumber);
        question.setPrompt_time(DateTime.getDateTime());
        setQuestionText(rootView, question);
        setLinearLayout(rootView, question);
        if (question.isType(Questions.IMAGE))
            setTypeImage(rootView, question);
        else if (question.isType(Questions.MULTIPLE_CHOICE) || question.isType(Questions.MULTIPLE_SELECT))
            setTypeMultipleChoiceSelect(rootView, question);
        return rootView;
    }

    void setTypeImage(ViewGroup rootView, Question question) {
        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.layout_horizontal_multiple_choice);
        ll.setGravity(Gravity.BOTTOM);
        ImageView imageView = new ImageView(this.getActivity());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setAdjustViewBounds(true);

        if (question.getQuestion_responses().get(0).equals("peak"))
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.peak));
        if (question.getQuestion_responses().get(0).equals("react"))
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.react));
        if (question.getQuestion_responses().get(0).equals("wave"))
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.wave));
        ll.addView(imageView);
    }

    private View addView() {
        View view = new View(this.getActivity());
        view.setLayoutParams(new LinearLayout.LayoutParams(100, 10));
        return view;
    }


    private ToggleButton addToggleButtons(final Question question, int response_id) {
        ToggleButton toggleButton = new ToggleButton(this.getActivity());
        String option = question.getQuestion_responses().get(response_id);
        Log.d(TAG, "addToggleButtons() option=" + option);
        toggleButton.setTextOn(option);
        toggleButton.setTextOff(option);
        toggleButton.setText(option);
        if (question.isResponseExist(option))
            toggleButton.setChecked(true);
        else toggleButton.setChecked(false);
        return toggleButton;
    }
}

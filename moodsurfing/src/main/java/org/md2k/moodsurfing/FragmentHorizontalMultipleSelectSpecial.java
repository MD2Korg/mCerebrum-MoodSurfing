package org.md2k.moodsurfing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import org.md2k.datakitapi.time.DateTime;


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
public class FragmentHorizontalMultipleSelectSpecial extends FragmentBase {
    private static final String TAG = FragmentHorizontalMultipleSelectSpecial.class.getSimpleName();

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static FragmentHorizontalMultipleSelectSpecial create(int exerciseType, int pageNumber) {
        FragmentHorizontalMultipleSelectSpecial fragment = new FragmentHorizontalMultipleSelectSpecial();
        fragment.setArguments(getArgument(exerciseType, pageNumber));
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.

        final ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_horizontal_multiple_select_special, container, false);

        question.setPrompt_time(DateTime.getDateTime());
        setQuestionText(rootView, question);
        setListener(rootView);
        return rootView;
    }

    void setListenerT1T2T3(final ToggleButton t1, final ToggleButton t2, final ToggleButton t3,final String textNa) {
        t1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    question.getQuestion_responses_selected().remove(t1.getText().toString());
                    question.getQuestion_responses_selected().add(t1.getText().toString());
                    question.getQuestion_responses_selected().remove(t2.getText().toString());
                    question.getQuestion_responses_selected().remove(textNa);
                    t2.setChecked(false);
                    t3.setChecked(false);
                } else {
                    question.getQuestion_responses_selected().remove(t1.getText().toString());
                }
                updateNext(isAnswered());
            }
        });
        t2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    question.getQuestion_responses_selected().remove(t2.getText().toString());
                    question.getQuestion_responses_selected().add(t2.getText().toString());
                    question.getQuestion_responses_selected().remove(t1.getText().toString());
                    question.getQuestion_responses_selected().remove(textNa);
                    t1.setChecked(false);
                    t3.setChecked(false);
                } else {
                    question.getQuestion_responses_selected().remove(t2.getText().toString());
                }
                updateNext(isAnswered());
            }
        });
        t3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    question.getQuestion_responses_selected().remove(textNa);
                    question.getQuestion_responses_selected().add(textNa);
                    question.getQuestion_responses_selected().remove(t1.getText().toString());
                    question.getQuestion_responses_selected().remove(t2.getText().toString());
                    t1.setChecked(false);
                    t2.setChecked(false);
                } else {
                    question.getQuestion_responses_selected().remove(textNa);
                }
                updateNext(isAnswered());
            }
        });
    }
    public boolean isAnswered(){
        return question.getQuestion_responses_selected().size()==4;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        updateNext(isAnswered());
    }

    void setListener(final ViewGroup rootView) {
        setListenerT1T2T3((ToggleButton) rootView.findViewById(R.id.toggleButton_small), (ToggleButton) rootView.findViewById(R.id.toggleButton_large), (ToggleButton) rootView.findViewById(R.id.toggleButton_sl_na),"Small_Large_NA");
        setListenerT1T2T3((ToggleButton) rootView.findViewById(R.id.toggleButton_smooth), (ToggleButton) rootView.findViewById(R.id.toggleButton_rough), (ToggleButton) rootView.findViewById(R.id.toggleButton_sr_na),"Smooth_Rough_NA");
        setListenerT1T2T3((ToggleButton) rootView.findViewById(R.id.toggleButton_soft), (ToggleButton) rootView.findViewById(R.id.toggleButton_hard), (ToggleButton) rootView.findViewById(R.id.toggleButton_sh_na),"Soft_Hard_NA");
        setListenerT1T2T3((ToggleButton) rootView.findViewById(R.id.toggleButton_heavy), (ToggleButton) rootView.findViewById(R.id.toggleButton_light), (ToggleButton) rootView.findViewById(R.id.toggleButton_hl_na),"Heavy_Light_NA");
    }
}

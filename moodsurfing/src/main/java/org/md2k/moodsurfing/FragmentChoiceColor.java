package org.md2k.moodsurfing;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.md2k.datakitapi.time.DateTime;
import org.md2k.utilities.Report.Log;

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
public class FragmentChoiceColor extends FragmentBase {
    private static final String TAG = FragmentChoiceColor.class.getSimpleName();


    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static FragmentChoiceColor create(int exerciseType, int pageNumber) {
        FragmentChoiceColor fragment = new FragmentChoiceColor();
        fragment.setArguments(getArgument(exerciseType,pageNumber));
        return fragment;
    }
    public boolean isAnswered(){
        Log.d(TAG,"isAnswered()="+(question.getQuestion_responses_selected().size() > 0));
        return question.getQuestion_responses_selected().size() > 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        updateNext(isAnswered());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() mPageNumber=" + mPageNumber);
        final ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_choice_color, container, false);
        question.setPrompt_time(DateTime.getDateTime());
        setQuestionText(rootView, question);
        GridView gridViewColors = (GridView) rootView.findViewById(R.id.gridViewColors);
        gridViewColors.setAdapter(new ColorPickerAdapter(getActivity()));


        // close the dialog on item click
        gridViewColors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> color=new ArrayList<String>();
                color.add(ColorPickerAdapter.colors[position / 6][position % 6]);
                question.setQuestion_responses_selected(color);
                updateNext(isAnswered());

            }
        });
        return rootView;
    }
}

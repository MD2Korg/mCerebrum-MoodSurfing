package org.md2k.moodsurfing;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.md2k.utilities.Report.Log;


/*
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
public class FragmentBase extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";
    public static final String ARG_TYPE = "type";
    private static final String TAG = FragmentBase.class.getSimpleName();
    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    protected int mPageNumber;
    protected int mExerciseType;
    protected Question question = null;
    private MediaPlayer mPlayer;
    private Menu menu = null;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    protected static Bundle getArgument(int exerciseType, int pageNumber) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putInt(ARG_TYPE, exerciseType);
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "FragmentBase-> onCreate()");
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        mExerciseType = getArguments().getInt(ARG_TYPE);
        question = Questions.getInstance().getQuestion(mExerciseType, mPageNumber);
        if (question.getAudio_R_raw() == -1) mPlayer = null;
        else mPlayer = MediaPlayer.create(getActivity(), question.getAudio_R_raw());
        setHasOptionsMenu(true);
    }

    protected void setQuestionText(ViewGroup rootView, Question question) {
        String question_text = question.getQuestion_text();
        if (question_text.contains("_____")) {
            String replace = Questions.getInstance().getQuestion(Constants.NOTICE_AND_ACCEPT, 2).getQuestion_responses_selected_random();
            if ("Other physical sensations".equals(replace)) {
                replace = "physical sensations";
                while (question_text.contains("Is it changing or does it"))
                    question_text = question_text.replace("Is it changing or does it", "Are they changing or do they");
                while (question_text.contains("Is"))
                    question_text = question_text.replace("Is", "Are");
            }
            Log.d(TAG, "" + Questions.getInstance().getQuestion(Constants.NOTICE_AND_ACCEPT, 2).getQuestion_responses_selected() + " " +
                    Questions.getInstance().getQuestion(Constants.NOTICE_AND_ACCEPT, 2).getQuestion_responses_selected_random());
            question_text = question_text.replace("_____", "\"" + replace + "\"");
            ((TextView) rootView.findViewById(R.id.textViewDescription)).setText(question_text);
            Log.d(TAG,"id="+ question.getQuestion_id()+" replace="+replace.toLowerCase());
            if(replace.toLowerCase().contains("craving"))
                question.setAudio_R_raw(question.getQuestion_id()==4?R.raw.naa_04_craving:R.raw.naa_05_craving);
            else if(replace.toLowerCase().contains("irritability"))
                question.setAudio_R_raw(question.getQuestion_id()==4?R.raw.naa_04_irritability:R.raw.naa_05_irritability);
            else if(replace.toLowerCase().contains("pain"))
                question.setAudio_R_raw(question.getQuestion_id()==4?R.raw.naa_04_pain:R.raw.naa_05_pain);
            else if(replace.toLowerCase().contains("restlessness"))
                question.setAudio_R_raw(question.getQuestion_id()==4?R.raw.naa_04_restlessness:R.raw.naa_05_restlessness);
            else if(replace.toLowerCase().contains("sensations"))
                question.setAudio_R_raw(question.getQuestion_id()==4?R.raw.naa_04_sensations:R.raw.naa_05_sensations);
            else if(replace.toLowerCase().contains("shakiness"))
                question.setAudio_R_raw(question.getQuestion_id()==4?R.raw.naa_04_shakiness:R.raw.naa_05_shakiness);
            else if(replace.toLowerCase().contains("tension"))
                question.setAudio_R_raw(question.getQuestion_id()==4?R.raw.naa_04_tension:R.raw.naa_05_tension);
            if(question.getAudio_R_raw()==-1) mPlayer=null;
            else mPlayer = MediaPlayer.create(getActivity(), question.getAudio_R_raw());
        } else if (question_text.contains("[speaker icon]")) {
            int index = question_text.indexOf("[speaker icon]");
            ImageSpan imageSpan=new ImageSpan(getActivity(), R.drawable.volume_enable);
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(question_text.substring(0, index)).append("  ");
            builder.setSpan(imageSpan,
                    builder.length() - 1, builder.length(), 0);
            builder.append("  ").append(question_text.substring(index + 14));
            ((TextView) rootView.findViewById(R.id.textViewDescription)).setText(builder);
        } else
            ((TextView) rootView.findViewById(R.id.textViewDescription)).setText(question_text);
    }

    public void updateNext(boolean answered) {
        if (menu != null)
            menu.findItem(R.id.action_next).setEnabled(answered);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        Log.d(TAG, "fragmentBase -> onCreateOptionsMenu");
        if (question.getAudio_R_raw() == -1) {
            menu.findItem(R.id.action_audio).setIcon(R.drawable.volume_disable);
            menu.findItem(R.id.action_audio).setEnabled(false);
        } else {
            menu.findItem(R.id.action_audio).setIcon(R.drawable.volume_enable);
            menu.findItem(R.id.action_audio).setEnabled(true);
        }
        updateNext(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "fragmentBase -> onOptionsItemSelected ->" + item.getItemId());
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.action_previous:
                Log.d(TAG, "fragmentBase -> onOptionsItemSelected -> previous");
                break;
            case R.id.action_audio:
                if (mPlayer.isPlaying())
                    mPlayer.pause();
                else
                    mPlayer.start();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setLinearLayout(ViewGroup rootView, Question question) {
        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.layout_horizontal_multiple_choice);
        if (question.getQuestion_type() != null && question.getQuestion_responses().size() > 3)
            ll.setOrientation(LinearLayout.VERTICAL);
        else ll.setOrientation(LinearLayout.HORIZONTAL);
    }

    protected View addView() {
        View view = new View(this.getActivity());
        view.setLayoutParams(new LinearLayout.LayoutParams(100, 10));
        return view;
    }

    @Override
    public void onDestroy() {
        if (mPlayer != null)
            if (mPlayer.isPlaying()) mPlayer.stop();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        if (mPlayer != null)
            if (mPlayer.isPlaying()) mPlayer.pause();
        super.onPause();
    }
}

package org.md2k.moodsurfing;

import android.app.Fragment;
import android.graphics.Typeface;
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

import org.md2k.dklibrary.Logger.Log;
import org.md2k.dklibrary.functions.DateTime;

import java.util.ArrayList;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 */
public class FragmentHorizontalMultipleSelectSpecial extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";
    public static final String ARG_TYPE = "type";
    private static final String TAG = FragmentHorizontalMultipleSelectSpecial.class.getSimpleName();

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    private int mExerciseType;
    Question question = null;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static FragmentHorizontalMultipleSelectSpecial create(int exerciseType, int pageNumber) {
        FragmentHorizontalMultipleSelectSpecial fragment = new FragmentHorizontalMultipleSelectSpecial();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putInt(ARG_TYPE, exerciseType);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentHorizontalMultipleSelectSpecial() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        mExerciseType = getArguments().getInt(ARG_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        question = Questions.getInstance().getQuestion(mExerciseType, mPageNumber);

        final ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_horizontal_multiple_select_special, container, false);
        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.layout_horizontal_multiple_choice);

        ((TextView) rootView.findViewById(R.id.textViewDescription)).setText(question.getQuestion_text());
        setListener(rootView);
        return rootView;
    }

    void setListenerT1T2(final ToggleButton t1, final ToggleButton t2) {
        t1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    question.getQuestion_responses_selected().remove(t1.getTextOn().toString());
                    question.getQuestion_responses_selected().add(t1.getTextOn().toString());
                    question.getQuestion_responses_selected().remove(t2.getTextOn().toString());
                    t2.setChecked(false);
                } else {
                    question.getQuestion_responses_selected().remove(t1.getTextOn().toString());
                }
                Log.d(TAG, "size=" + question.getQuestion_responses_selected().size());
            }
        });
        t2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    question.getQuestion_responses_selected().remove(t2.getTextOn().toString());
                    question.getQuestion_responses_selected().add(t2.getTextOn().toString());
                    question.getQuestion_responses_selected().remove(t1.getTextOn().toString());
                    t1.setChecked(false);
                } else {
                    question.getQuestion_responses_selected().remove(t2.getTextOn().toString());
                }
                Log.d(TAG, "size=" + question.getQuestion_responses_selected().size());
            }
        });
    }

    void setListener(final ViewGroup rootView) {
        setListenerT1T2((ToggleButton) rootView.findViewById(R.id.toggleButton_small), (ToggleButton) rootView.findViewById(R.id.toggleButton_large));
        setListenerT1T2((ToggleButton) rootView.findViewById(R.id.toggleButton_smooth), (ToggleButton) rootView.findViewById(R.id.toggleButton_rough));
        setListenerT1T2((ToggleButton) rootView.findViewById(R.id.toggleButton_soft), (ToggleButton) rootView.findViewById(R.id.toggleButton_hard));
        setListenerT1T2((ToggleButton) rootView.findViewById(R.id.toggleButton_heavy), (ToggleButton) rootView.findViewById(R.id.toggleButton_light));
    }
}

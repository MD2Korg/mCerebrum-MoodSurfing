package org.md2k.moodsurfing;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.md2k.utilities.Report.Log;

public class ActivityMoodSurfingExercise extends Activity {
    private static final String TAG = ActivityMoodSurfingExercise.class.getSimpleName();
    private NonSwipeableViewPager mPager;

    private PagerAdapter mPagerAdapter;
    private int exerciseType;
    Question[] questions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exerciseType = getIntent().getIntExtra("type", -1);
        questions=Questions.getInstance().getQuestions(exerciseType);
        setContentView(R.layout.activity_mood_surfing_exercise);
        setTitle(Constants.BEGIN_TITLE[exerciseType]);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (NonSwipeableViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
                Log.d(TAG, "viewpager: onPageSelected: position=" + position);
                invalidateOptionsMenu();
            }
        });
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_mood_surfing_exercise, menu);

        menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem() > 0);

        // Add either a "next" or "finish" button to the action bar, depending on which page
        // is currently selected.
        MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
                (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)
                        ? R.string.action_finish
                        : R.string.action_next);
        if(mPager.getCurrentItem()==mPagerAdapter.getCount()-1)
            item.setIcon((R.drawable.ic_done_black_24dp));
        else
        item.setIcon(R.drawable.ic_keyboard_arrow_right_black_48dp);

        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Window window = getWindow();
                View v = window.getDecorView();
                int resId = getResources().getIdentifier("home", "id", "android");
//                return v.findViewById(resId);
                PopupMenu popup = new PopupMenu(getActionBar().getThemedContext(), v.findViewById(resId));
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_options, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                NavUtils.navigateUpTo(ActivityMoodSurfingExercise.this, new Intent(ActivityMoodSurfingExercise.this, ActivityMoodSurfingExercises.class));
                                return true;
                            case R.id.action_supporting_literature:
                                return true;
                            case R.id.action_settings:
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popup.show();
                return true;
            case R.id.action_previous:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
                Log.d(TAG, "Previous button: " + mPager.getCurrentItem());
                mPager.getAdapter().notifyDataSetChanged();
                mPager.setCurrentItem(findValidQuestionPrevious(mPager.getCurrentItem()));
                return true;

            case R.id.action_next:
                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
                // will do nothing.
                //TODO: next when can be done
                Log.d(TAG, "Next button" + " current=" + mPager.getCurrentItem());
                if (!questions[mPager.getCurrentItem()].isValid()) {
                    Toast.makeText(getBaseContext(), "Please answer the question first", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (mPager.getCurrentItem() >= questions.length - 1) {
                    Questions.getInstance().destroy();
                    //TODO: send data to datakit
                    finish();
                }
                if (questions[mPager.getCurrentItem()].isValid()) {
                    mPager.getAdapter().notifyDataSetChanged();
                    mPager.setCurrentItem(findValidQuestionNext(mPager.getCurrentItem()));
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    int findValidQuestionPrevious(int cur) {
        cur--;
        while (cur >= 0) {
            if (!questions[cur].isValidCondition(questions))
                cur--;
            else break;
        }
        return cur;
    }

    int findValidQuestionNext(int cur) {
        cur++;
        while (cur < questions.length) {
            if (!questions[cur].isValidCondition(questions))
                cur++;
            else break;
        }
        return cur;
    }



    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "getItem(): position=" + position);
            if (questions[position].isType(Questions.MULTIPLE_SELECT_SPECIAL))
                return FragmentHorizontalMultipleSelectSpecial.create(exerciseType, position);
            else return FragmentChoiceSelectImage.create(exerciseType, position);
        }

        @Override
        public int getCount() {
            if (questions != null)
                return questions.length;
            else return 0;
        }
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}

package org.md2k.moodsurfing;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.md2k.datakitapi.time.DateTime;
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
public class ActivityMoodSurfingExercise extends Activity {
    private static final String TAG = ActivityMoodSurfingExercise.class.getSimpleName();
    private NonSwipeableViewPager mPager;
    private FragmentBase fragmentBase;

    private PagerAdapter mPagerAdapter;
    private int exerciseType;
    private Question[] questions = null;
    private PopupMenu popup = null;
    boolean flag;
    public static ActivityMoodSurfingExercise fa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Questions.getInstance().setStartTime(DateTime.getDateTime());
        fa=this;
        exerciseType = getIntent().getIntExtra("type", -1);
        questions = Questions.getInstance().getQuestions(exerciseType);
        flag=false;
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
        if (getActionBar() != null)
            getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "Activity -> onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_mood_surfing_exercise, menu);
        menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem() > 0);

        // Add either a "next" or "finish" button to the action bar, depending on which page
        // is currently selected.
        MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
                (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)
                        ? R.string.action_finish
                        : R.string.action_next);
        if (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)
            item.setTitle("Finish");
        else
            item.setTitle("Next");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (popup != null) popup.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (popup == null) {
                    Window window = getWindow();
                    View v = window.getDecorView();
                    int resId = getResources().getIdentifier("home", "id", "android");
                    if (getActionBar() == null) break;
                    popup = new PopupMenu(getActionBar().getThemedContext(), v.findViewById(resId));
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.menu_options, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_home:
                                    finish();
//                                    NavUtils.navigateUpTo(ActivityMoodSurfingExercise.this, new Intent(ActivityMoodSurfingExercise.this, ActivityMoodSurfingExercises.class));
                                    break;
                                case R.id.action_supporting_literature:
                                    Intent intentL = new Intent(ActivityMoodSurfingExercise.this, ActivityLiterature.class);
                                    startActivity(intentL);
                                    break;
                                case R.id.action_exit:
                                    finish();
                                    break;
                                case R.id.action_about:
                                    Intent intent = new Intent(ActivityMoodSurfingExercise.this, ActivityAbout.class);
                                    startActivity(intent);
                                    break;
                                case R.id.action_copyright:
                                    Intent intentC = new Intent(ActivityMoodSurfingExercise.this, ActivityCopyright1.class);
                                    startActivity(intentC);
                                    break;

                                default:
                                    break;
                            }
                            return true;
                        }
                    });
                }
                popup.show();

                break;
            case R.id.action_previous:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
                Log.d(TAG, "activity -> onOptionsItemSelected -> previous");

                Log.d(TAG, "Previous button: " + mPager.getCurrentItem());
                mPager.getAdapter().notifyDataSetChanged();
                mPager.setCurrentItem(findValidQuestionPrevious(mPager.getCurrentItem()));
                if(Questions.VIDEO.equals(questions[mPager.getCurrentItem()].getQuestion_type()))
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                else
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case R.id.action_next:
                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
                // will do nothing.
                Log.d(TAG, "Next button" + " current=" + mPager.getCurrentItem());
                if (!questions[mPager.getCurrentItem()].isValid()) {
                    Toast.makeText(getBaseContext(), "Please answer the question first", Toast.LENGTH_SHORT).show();
                } else if (mPager.getCurrentItem() >= questions.length - 1) {
                    Questions.getInstance().setEndTime(DateTime.getDateTime());
                    Questions.getInstance().setStatus(Constants.COMPLETED);
                    QuestionAnswer.getInstance(ActivityMoodSurfingExercise.this).add(new QuestionsJSON(Questions.getInstance(), exerciseType));
                    Questions.getInstance().destroy();
                    flag=true;
                    finish();
                } else if (questions[mPager.getCurrentItem()].isValid()) {
                    questions[mPager.getCurrentItem()].setCompletion_time(DateTime.getDateTime());
                    mPager.getAdapter().notifyDataSetChanged();
                    mPager.setCurrentItem(findValidQuestionNext(mPager.getCurrentItem()));
                }
                if(Questions.VIDEO.equals(questions[mPager.getCurrentItem()].getQuestion_type()))
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                else
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private int findValidQuestionPrevious(int cur) {
        cur--;
        while (cur >= 0) {
            if (!questions[cur].isValidCondition(questions))
                cur--;
            else break;
        }
        return cur;
    }

    private int findValidQuestionNext(int cur) {
        cur++;
        while (cur < questions.length) {
            if (!questions[cur].isValidCondition(questions))
                cur++;
            else break;
        }
        return cur;
    }


    @Override
    public void onBackPressed() {
        showAlertDialog();

    }

    private void showAlertDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Quit?")
                .setIcon(R.drawable.ic_error_grey_50dp)
                .setMessage("Do you want to quit from this exercise?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Questions.getInstance().setEndTime(DateTime.getDateTime());
                        Questions.getInstance().setStatus(Constants.ABANDONED_BY_USER);
                        QuestionAnswer.getInstance(ActivityMoodSurfingExercise.this).add(new QuestionsJSON(Questions.getInstance(), exerciseType));
                        flag=true;
                        Questions.getInstance().destroy();
                        finish();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create();

        alertDialog.show();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "getItem(): position=" + position);
            if (questions[position].isType(Questions.MULTIPLE_SELECT_SPECIAL)) {
                fragmentBase = FragmentHorizontalMultipleSelectSpecial.create(exerciseType, position);
            } else if (questions[position].isType(Questions.COLOR))
                fragmentBase = FragmentChoiceColor.create(exerciseType, position);
            else if (questions[position].isType(Questions.VIDEO)) {
                fragmentBase = FragmentVideo.create(exerciseType, position);
            }
            else fragmentBase = FragmentChoiceSelectImage.create(exerciseType, position);
            return fragmentBase;
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
    public void saveUnsavedData(){
        Questions.getInstance().setEndTime(DateTime.getDateTime());
        Questions.getInstance().setStatus(Constants.ABANDONED_BY_TIMEOUT);
        QuestionAnswer.getInstance(ActivityMoodSurfingExercise.this).add(new QuestionsJSON(Questions.getInstance(), exerciseType));
        Questions.getInstance().destroy();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy()...");
/*        if(flag==false){
            Questions.getInstance().setEndTime(DateTime.getDateTime());
            Questions.getInstance().setStatus(Constants.ABANDONED_BY_TIMEOUT);
            QuestionAnswer.getInstance(ActivityMoodSurfingExercise.this).add(new QuestionsJSON(Questions.getInstance(), exerciseType));
            flag=true;
        }
*/
        super.onDestroy();
    }
}

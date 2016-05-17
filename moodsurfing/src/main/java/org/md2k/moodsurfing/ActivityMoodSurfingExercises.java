package org.md2k.moodsurfing;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupMenu;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;

import org.md2k.utilities.Report.Log;

import io.fabric.sdk.android.Fabric;


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
public class ActivityMoodSurfingExercises extends Activity {
    private static final String TAG = ActivityMoodSurfingExercises.class.getSimpleName();
    private MyBroadcastReceiver myReceiver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up Crashlytics, disabled for debug builds
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();

        // Initialize Fabric with the debug-disabled crashlytics.
        Fabric.with(this, crashlyticsKit);


        Log.d(TAG, "onCreate()...");
        QuestionAnswer.clear();
        QuestionAnswer.getInstance(this);
        setContentView(R.layout.activity_mood_surfing_exercises);

        Button button;
        button = (Button) findViewById(R.id.buttonImagination);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMoodSurfingExercises.this, ActivityMoodSurfingExercise.class);
                intent.putExtra("type", Constants.USE_YOUR_IMAGINATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Questions.getInstance().clear(Constants.USE_YOUR_IMAGINATION);
                startActivity(intent);
            }
        });
        button = (Button) findViewById(R.id.buttonNotice);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMoodSurfingExercises.this, ActivityMoodSurfingExercise.class);
                intent.putExtra("type", Constants.NOTICE_AND_ACCEPT);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Questions.getInstance().clear(Constants.NOTICE_AND_ACCEPT);
                startActivity(intent);
            }
        });
        button = (Button) findViewById(R.id.buttonSurfTheMood);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMoodSurfingExercises.this, ActivityMoodSurfingExercise.class);
                intent.putExtra("type", Constants.SURF_THE_MOOD);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Questions.getInstance().clear(Constants.USE_YOUR_IMAGINATION);
                startActivity(intent);
            }
        });
        if (getActionBar() != null)
            getActionBar().setDisplayHomeAsUpEnabled(true);
        intentFilter= new IntentFilter("org.md2k.ema.operation");
        myReceiver = new MyBroadcastReceiver(new Callback() {
            @Override
            public void onTimeOut() {
                Log.d(TAG,"timeout...");
                ActivityMoodSurfingExercise.fa.saveUnsavedData();
                ActivityMoodSurfingExercise.fa.finish();
                finish();
            }

            @Override
            public void onMissed() {
                Log.d(TAG,"timeout");
                finish();
            }
        });
        if (intentFilter != null) {
            registerReceiver(myReceiver, intentFilter);
        }


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
//                                NavUtils.navigateUpTo(ActivityMoodSurfingExercises.this, new Intent(ActivityMoodSurfingExercises.this, ActivityMoodSurfingExercises.class));
                                return true;
                            case R.id.action_supporting_literature:
                                Intent intentL=new Intent(ActivityMoodSurfingExercises.this, ActivityLiterature.class);
                                startActivity(intentL);
                                return true;
                            case R.id.action_exit:
                                finish();
                                return true;
                            case R.id.action_about:
                                Intent intent=new Intent(ActivityMoodSurfingExercises.this, ActivityAbout.class);
                                startActivity(intent);
                                return true;
                            case R.id.action_copyright:
                                Intent intentC=new Intent(ActivityMoodSurfingExercises.this, ActivityCopyright1.class);
                                startActivity(intentC);
                                return true;

                            default:
                                return false;
                        }
                    }
                });

                popup.show();//showing popup menu

                //              NavUtils.navigateUpFromSameTask(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()...");
        QuestionAnswer.getInstance(this).sendData();
        QuestionAnswer.clear();
        if(myReceiver != null)
            unregisterReceiver(myReceiver);
        super.onDestroy();
    }
}

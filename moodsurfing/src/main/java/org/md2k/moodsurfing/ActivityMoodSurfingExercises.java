package org.md2k.moodsurfing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import org.md2k.datakitapi.DataKitAPI;
import org.md2k.datakitapi.messagehandler.OnConnectionListener;
import org.md2k.datakitapi.messagehandler.OnExceptionListener;
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
    DataKitAPI dataKitAPI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        Log.d(TAG, "onCreate()...");
        setContentView(R.layout.activity_mood_surfing_exercises);
        connectDataKit();

        Button button;
        button = (Button) findViewById(R.id.buttonImagination);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMoodSurfingExercises.this, ActivityMoodSurfingExercise.class);
                intent.putExtra("type", Constants.USE_YOUR_IMAGINATION);
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
                Questions.getInstance().clear(Constants.USE_YOUR_IMAGINATION);
                startActivity(intent);
            }
        });
        if (getActionBar() != null)
            getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void connectDataKit() {
        Log.d(TAG, "connectDataKit()...");
        if (dataKitAPI == null)
            dataKitAPI = DataKitAPI.getInstance(getApplicationContext());
        if (dataKitAPI.isConnected()) return;
        dataKitAPI.connect(new OnConnectionListener() {
            @Override
            public void onConnected() {
            }
        }, new OnExceptionListener() {
            @Override
            public void onException(org.md2k.datakitapi.status.Status status) {
                Toast.makeText(ActivityMoodSurfingExercises.this, "ERROR: Can't connect with datakit...." + status.getStatusMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        });
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
        if(dataKitAPI!=null) {
            Log.d(TAG, "onDestroy()... isConnected=" + dataKitAPI.isConnected());
            if (dataKitAPI.isConnected())
                dataKitAPI.disconnect();
            dataKitAPI.close();
            dataKitAPI = null;
        }
        super.onDestroy();
    }
}

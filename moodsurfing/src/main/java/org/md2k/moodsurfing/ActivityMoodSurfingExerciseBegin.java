package org.md2k.moodsurfing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


public class ActivityMoodSurfingExerciseBegin extends Activity {
    int exerciseType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_surfing_exercise_begin);
        exerciseType = getIntent().getIntExtra("type", -1);
        Button buttonBegin;
        buttonBegin = (Button) findViewById(R.id.buttonBegin);

        buttonBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMoodSurfingExerciseBegin.this, ActivityMoodSurfingExercise.class);
                intent.putExtra("type", exerciseType);
                Questions.getInstance().clear(exerciseType);
                startActivity(intent);
                finish();
            }
        });
        TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewTitle.setText(Constants.BEGIN_TITLE[exerciseType]);
        TextView textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewDescription.setText(Constants.BEGIN_DESCRIPTION[exerciseType]);
        setTitle(Constants.BEGIN_TITLE[exerciseType]);
        getActionBar().setDisplayHomeAsUpEnabled(true);

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
                                NavUtils.navigateUpTo(ActivityMoodSurfingExerciseBegin.this,new Intent(ActivityMoodSurfingExerciseBegin.this, ActivityMoodSurfingExercises.class));
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

                popup.show();//showing popup menu

                //              NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

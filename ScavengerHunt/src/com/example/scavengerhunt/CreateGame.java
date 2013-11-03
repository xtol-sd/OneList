package com.example.scavengerhunt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class CreateGame extends Activity {
    
    private ParseUser currentUser;
    private ParseObject game = new ParseObject("Game");

    Calendar myCalendar = Calendar.getInstance();
    private Button createGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        setupButtonCallbacks();
    }

    public void onResume() {
        super.onResume();
        currentUser = ParseUser.getCurrentUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_game, menu);
        return true;
    }

    private void setupButtonCallbacks() {
        createGameButton = (Button) findViewById(R.id.button_CreateGame);
        createGameButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                doCreateGame();
                Intent intent = new Intent(CreateGame.this, ViewGame.class);
                intent.putExtra("gameId", getGameId());
                startActivity(intent);
            }
        });
    }

    private void doCreateGame() {
        game.put("name", getGameName());
        game.put("creator_id", currentUser);
        game.put("start_datetime", getStartDateTime());
        game.put("end_datetime", getEndDateTime());
        showToast("Game Created!");
        game.saveInBackground();
    }

    private String getGameId() {
        return game.getObjectId();
    }
    private void showToast(String message) {
        ScavengerHuntApplication.getInstance().showToast(CreateGame.this,
                message);
    }

//    private int getCreatorId() {
//        int i = 1;
//        return i;
//    }

    private String getGameName() {
        return getUserInput(R.id.editGameName);
    }

    private Date getStartDateTime() {
        return convertToDateTime(getUserInput(R.id.editStartDate) + " "
                + getUserInput(R.id.editStartTime));
    }

    private Date getEndDateTime() {
        return convertToDateTime(getUserInput(R.id.editEndDate) + " "
                + getUserInput(R.id.editEndTime));
    }

    private String getUserInput(int id) {
        EditText input = (EditText) findViewById(id);
        return input.getText().toString();
    }

    private Date convertToDateTime(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy h:mm a",
                Locale.US);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }

    public void showStartDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "startDatePicker");
    }

    public void showStartTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "startTimePicker");
    }

    public void showEndDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "endDatePicker");
    }

    public void showEndTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "endTimePicker");
    }

}
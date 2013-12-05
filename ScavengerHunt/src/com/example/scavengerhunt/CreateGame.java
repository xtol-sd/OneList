package com.example.scavengerhunt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class CreateGame extends Activity {

    private List<ParseUser> playerList = new ArrayList<ParseUser>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        setupListViews();
        setupButtonCallbacks();
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_game, menu);
        return true;
    }

    private void setupButtonCallbacks() {
        final Button createGameButton = (Button) findViewById(R.id.button_CreateGame);
        final Button resetGameButton = (Button) findViewById(R.id.button_ResetCreateGame);
        final Button addItemButton = (Button) findViewById(R.id.button_addItem);
        createGameButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                doCreateGame();
            }
        });
        resetGameButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                resetAllFields();
            }
        });
        addItemButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showItemAddDialog();
            }
        });
    }

    private void resetAllFields() {
        clearEditText(R.id.editStartTime);
        clearEditText(R.id.editEndDate);
        clearEditText(R.id.editEndTime);
        clearEditText(R.id.editStartDate);
        getItemAdapter().clear();
        // TODO uncheck boxes for GamePlayers
    }

    private void clearEditText(int i) {
        ((EditText) findViewById(i)).setText("");
    }

    private void launchGameView(String gameId) {
        Intent intent = new Intent(CreateGame.this, ViewGame.class);
        intent.putExtra("gameId", gameId);
        Log.d("GameId", "game id is " + gameId);
        startActivity(intent);
    }

    private void doCreateGame() {
        final ParseObject game = new ParseObject("Game");
        final ParseUser currentUser = ParseUser.getCurrentUser();
        game.put("name", getGameName());
        game.put("creator", currentUser);
        game.put("start_datetime", getStartDateTime());
        game.put("end_datetime", getEndDateTime());
        game.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("Game Creation", "Game Created!");
                    saveGamePlayers(getChosenPlayerList(), game);
                    saveGameItems(getItemList(), game);
                    showToast("Game Created!");
                    launchGameView(game.getObjectId());
                } else {
                    Log.d("Game Creation", "Error creating game: " + e);
                }
            }
        });
    }

    private ArrayList<String> getItemList() {
        ArrayList<String> itemList = new ArrayList<String>();
        ArrayAdapter<String> adapter = getItemAdapter();
        for (int i = 0; i < (adapter.getCount()); i++) {
            itemList.add(adapter.getItem(i));
        }
        return itemList;

    }

    private void saveGameItems(ArrayList<String> itemList, ParseObject game) {
        for (int i = 0; i < itemList.size(); i++) {
            final String item = itemList.get(i);
            Log.d("Item", item);
            final ParseObject gameItem = new ParseObject("GameItem");
            gameItem.put("name", item);
            gameItem.put("game", game);
            gameItem.saveInBackground(new SaveCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        Log.d("Save", "Item Saved");
                    } else {
                        Log.d("Save", "Error saving gameItem: " + e);
                    }
                }

            });
        }
    }

    private List<ParseUser> getChosenPlayerList() {
        final List<ParseUser> chosenPlayers = new ArrayList<ParseUser>();
        final ListView playerListView = (ListView) findViewById(R.id.listview_players);
        final SparseBooleanArray checkedItems = playerListView
                .getCheckedItemPositions();
        if (checkedItems != null) {
            for (int i = 0; i < checkedItems.size(); i++) {
                if (checkedItems.valueAt(i)) {
                    chosenPlayers.add(playerList.get(checkedItems.keyAt(i)));
                }
            }
        }
        return chosenPlayers;
    }

    private void saveGamePlayers(List<ParseUser> chosenPlayerList,
            ParseObject game) {
        for (int i = 0; i < chosenPlayerList.size(); i++) {
            ParseUser user = chosenPlayerList.get(i);
            Log.d("Player", user.toString());
            ParseObject gamePlayer = new ParseObject("GamePlayer");
            gamePlayer.put("user", user);
            gamePlayer.put("game", game);
            gamePlayer.saveInBackground(new SaveCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        Log.d("Save", "gamePlayer data saved!");
                    } else {
                        Log.d("Save", "Error saving gamePlayer: " + e);
                    }
                }

            });
        }
    }

    private void showToast(String message) {
        ScavengerHuntApplication.getInstance().showToast(CreateGame.this,
                message);
    }

    private String getGameName() {
        return getUserInput(R.id.edit_gameName);
    }

    private static Date convertToDateTime(String dateString) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MM-dd-yyyy h:mm a", Locale.US);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
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

    public void showStartDatePickerDialog(View v) {
        final DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "startDatePicker");
    }

    public void showStartTimePickerDialog(View v) {
        final DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "startTimePicker");
    }

    public void showEndDatePickerDialog(View v) {
        final DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "endDatePicker");
    }

    public void showEndTimePickerDialog(View v) {
        final DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "endTimePicker");
    }

    private void setupListViews() {
        setupItemListView();
        setParseUserList();
    }

    private void setupItemListView() {
        final ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        final ListView itemListView = (ListView) findViewById(R.id.listview_items);
        itemListView.setAdapter(itemAdapter);
        itemListView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent,
                            final View view, int position, long id) {
                        final String item = (String) parent
                                .getItemAtPosition(position);

                        itemAdapter.remove(item);
                        itemAdapter.notifyDataSetChanged();
                        view.setAlpha(1);
                    }
                });

    }

    private void showItemAddDialog() {
        final DialogFragment newFragment = new ItemAddDialogFragment();
        newFragment.show(getFragmentManager(), "createGameItems");
    }

    private void addItemToList(String item, ArrayAdapter<String> adapter) {
        adapter.add(item);
        adapter.notifyDataSetChanged();
    }

    public void onFinishItemDialog(String item) {
        addItemToList(item, getItemAdapter());
    }

    private ArrayAdapter<String> getItemAdapter() {
        final ListView itemListView = (ListView) findViewById(R.id.listview_items);
        final ArrayAdapter<String> adapter = (ArrayAdapter<String>) itemListView
                .getAdapter();
        return adapter;
    }

    private void setupUsernameListView(String[] usernameList) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, usernameList);
        final ListView playerListView = (ListView) findViewById(R.id.listview_players);
        playerListView.setAdapter(adapter);
        playerListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    private void setParseUserList() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.selectKeys(Arrays.asList("username"));
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userList, ParseException e) {
                if (e == null) {
                    String[] usernameList = new String[userList.size()];
                    Log.d("User List", "Retrieved " + userList.size());
                    for (int i = 0; i < userList.size(); i++) {
                        Log.d("data", "Retrieved User: "
                                + userList.get(i).getString("username"));
                        usernameList[i] = userList.get(i).getString("username");
                    }
                    playerList = userList;
                    setupUsernameListView(usernameList);
                } else {
                    Log.w("error", "game retreival failure");
                }
            }
        });

    }
    
}
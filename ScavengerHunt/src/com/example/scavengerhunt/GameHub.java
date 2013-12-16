package com.example.scavengerhunt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class GameHub extends Activity {

    ParseObject game;
    List<ParseObject> userFoundItems;

    final ParseUser currentUser = ParseUser.getCurrentUser();
    int currentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle extras = getIntent().getExtras();
        final String gameId = extras.getString("gameId");
        setContentView(R.layout.activity_game_hub);
        getGame(gameId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_hub, menu);
        return true;
    }

    private void getGame(final String gameId) {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Game");
        query.getInBackground(gameId, new GetCallback<ParseObject>() {
            public void done(ParseObject currentGame, ParseException e) {
                if (e == null) {
                    game = currentGame;
                    setGameContent();
                } else {
                    Log.w("error", "game retrieval failure");
                }
            }
        });
    }

    private void setGameContent() {
        final TextView gameName = (TextView) findViewById(R.id.title_gameName);
        final TextView endDatetime = (TextView) findViewById(R.id.text_endDatetime);

        gameName.setText(game.getString("name"));
        endDatetime.setText(game.getDate("end_datetime").toString());
        setRemainingItemsListView();
    }

    private void setRemainingItemsListView() {
        initializeItemListView();
        getUserFoundItems();
    }

    private void initializeItemListView() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        final ListView itemListView = (ListView) findViewById(R.id.listview_remainingItems);
        itemListView.setAdapter(adapter);
        itemListView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                            final View view, int position, long id) {
                        Log.d("Dialog Values",
                                "Position is " + String.valueOf(position)
                                        + ". View is " + view.toString()
                                        + ". ID is " + String.valueOf(id));
                        final String itemName = (String) parent
                                .getItemAtPosition(position);
                        showItemFoundDialog(itemName);
                    }
                });

    }

    private void showItemFoundDialog(final String itemName) {
        Bundle item = new Bundle();
        item.putString("name", itemName);
        final DialogFragment itemFoundDialogFragment = new ItemFoundDialogFragment();
        itemFoundDialogFragment.setArguments(item);
        itemFoundDialogFragment.show(getFragmentManager(), "itemFound");
    }

    private void deleteAlreadyFoundItems() {
        for (final String listItem : getItemListViewItems()) {
            for (final ParseObject foundItem : userFoundItems) {
                if (listItem.equals(foundItem.getString("item"))) {
                    deleteListItem(listItem);
                }
            }
        }
    }

    private ArrayList<String> getItemListViewItems() {
        final ArrayList<String> itemList = new ArrayList<String>();
        final ArrayAdapter<String> adapter = getItemAdapter();
        for (int i = 0; i < (adapter.getCount()); i++) {
            itemList.add(adapter.getItem(i));
        }
        return itemList;

    }

    private void getUserFoundItems() {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("FoundItem");
        query.whereEqualTo("game", game);
        query.whereEqualTo("user", currentUser);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> foundItems,
                    ParseException e) {
                if (e == null) {
                    userFoundItems = foundItems;
                    addAllGameItems();
                } else {
                    Log.w("Parse Error", "player username retrieval failure");
                }
            }
        });

    }

    private void addAllGameItems() {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("GameItems");
        query.whereEqualTo("game", game);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(final ParseObject gameItems, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < gameItems.getJSONArray("items")
                            .length(); i++) {
                        try {
                            addToListView(gameItems.getJSONArray("items")
                                    .getString(i), getItemAdapter());
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                    setScore(gameItems.getJSONArray("items"));
                    deleteAlreadyFoundItems();
                } else {
                    Log.w("Parse Error", "game items retrieval failure");
                }
            }
        });

    }

    private void setScore(final JSONArray totalItems) {
        currentScore = userFoundItems.size();
        final TextView scoreView = (TextView) findViewById(R.id.text_score);
        final TextView totalPointsView = (TextView) findViewById(R.id.text_totalPoints);
        scoreView.setText(String.valueOf(currentScore));
        totalPointsView.setText(" out of " + totalItems.length());
    }

    private void addToListView(final String item,
            final ArrayAdapter<String> adapter) {
        adapter.add(item);
        adapter.notifyDataSetChanged();
    }

    private ArrayAdapter<String> getItemAdapter() {
        final ListView itemListView = (ListView) findViewById(R.id.listview_remainingItems);
        final ArrayAdapter<String> adapter = (ArrayAdapter<String>) itemListView
                .getAdapter();
        return adapter;
    }

    public void onFoundItemDialog(final String name) {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Game");
        query.whereExists("winner");
        query.getInBackground(game.getObjectId(), new GetCallback<ParseObject>() {
            public void done(ParseObject currentGame, ParseException e) {
                if (e == null) {
                    if(currentGame == null){
                        sendFoundItemToParse(name);
                        deleteListItem(name);
                        incrementScore();
                        checkGameFinish();
                    }
                    else{
                        launchAlreadyWonDialog();
                    }
                } else {
                    Log.w("error", "game retrieval error");
                }
            }

        });
    }

    private void launchAlreadyWonDialog() {
        final DialogFragment alreadyWonDialog = new AlreadyWonDialogFragment();
        alreadyWonDialog.show(getFragmentManager(), "AreadyWon");

    }

    private void sendFoundItemToParse(final String name) {
        final ParseObject foundItem = new ParseObject("FoundItem");
        foundItem.put("game", game);
        foundItem.put("user", currentUser);
        foundItem.put("item", name);
        foundItem.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("GameHub", "Item Found!");
                } else {
                    Log.d("GameHub", "Error creating found item: " + e);
                }
            }
        });
    }

    private void deleteListItem(final String item) {
        final ArrayAdapter<String> adapter = getItemAdapter();
        adapter.remove(item);
        adapter.notifyDataSetChanged();
    }

    private void incrementScore() {
        currentScore++;
        final TextView scoreView = (TextView) findViewById(R.id.text_score);
        scoreView.setText(String.valueOf(currentScore));
    }

    private void checkGameFinish() {
        if (getItemListViewItems().size() == 0) {
            setGameWinner();
        }
    }

    private void setGameWinner() {
        game.put("winner", currentUser);
        game.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("GameHub",
                            "Winner Saved to game named "
                                    + game.getString("name") + "!");
                    getGamePlayers();
                } else {
                    Log.d("GameHub", "Error in saving winner: " + e);
                }
            }
        });
    }

    private void sendWinnerPush(final List<ParseUser> gamePlayers) {
        for (final ParseUser player : gamePlayers) {
            final ParseQuery<ParseInstallation> pushQuery = ParseInstallation
                    .getQuery();
            pushQuery.whereEqualTo("owner", player);

            final ParsePush push = new ParsePush();
            push.setQuery(pushQuery);
            push.setMessage(currentUser.getString("username") + " has won the "
                    + game.getString("name")
                    + " scavenger hunt. Thank you for playing!");
            push.sendInBackground();
        }
    }

    private void getGamePlayers() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GamePlayers");
        query.whereEqualTo("game", game);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject players, ParseException e) {
                if (e == null) {
                    Log.d("User List", "Retrieved " + players.toString());
                    getUsers(players);
                } else {
                    Log.w("Parse Error", "game retreival failure");
                }
            }
        });
    }

    private void getUsers(ParseObject gamePlayers) {
        final String[] gamePlayerIds = new String[gamePlayers.getJSONArray(
                "users").length()];
        final JSONArray userInfo = gamePlayers.getJSONArray("users");
        for (int i = 0; i < userInfo.length(); i++) {
            JSONObject user = null;
            try {
                user = userInfo.getJSONObject(i);
            } catch (JSONException e1) {

                e1.printStackTrace();
            }
            String userId = null;
            try {
                userId = user.getString("objectId");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            gamePlayerIds[i] = userId;
        }

        final ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereContainedIn("objectId", Arrays.asList(gamePlayerIds));
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(final List<ParseUser> users, ParseException e) {
                if (e == null) {
                    sendWinnerPush(users);
                } else {
                    Log.w("Parse Error", "player username retrieval failure");
                }
            }
        });
    }

}

package com.example.scavengerhunt;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ViewGame extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle extras = getIntent().getExtras();
        final String gameId = extras.getString("gameId");
        getGame();
        setContentView(R.layout.activity_view_game);
        setupButtonCallbacks(gameId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_game, menu);
        return true;
    }

    private void setupButtonCallbacks(final String gameId) {
        final Button editGameButton = (Button) findViewById(R.id.button_editGame);
        editGameButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                doEditGame(gameId);
            }
        });

    }

    public void doEditGame(final String gameId) {
        Intent intent = new Intent(ViewGame.this, EditGame.class);
        intent.putExtra("gameId", gameId);
        Log.d("GameId", "game id is " + gameId);
        startActivity(intent);
    }

    public void onResume() {
        super.onResume();
    }

    public void setGameInfo(ParseObject game) {
        initializeListViews();

        TextView gameName = (TextView) findViewById(R.id.text_gameName);
        TextView startDatetime = (TextView) findViewById(R.id.text_startDatetime);
        TextView endDatetime = (TextView) findViewById(R.id.text_endDatetime);

        gameName.setText(game.getString("name"));
        startDatetime.setText(game.getDate("start_datetime").toString());
        endDatetime.setText(game.getDate("end_datetime").toString());
        setItemList(game);
        setPlayerList(game);
    }

    private void initializeListViews() {
        setupUsernameListView();
        setupItemListView();
    }

    private void setPlayerList(ParseObject game) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GamePlayers");
        query.whereEqualTo("game", game);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject gamePlayers, ParseException e) {
                if (e == null) {
                    getUsernameList(gamePlayers);
                } else {
                    Log.w("Parse Error", "game retreival failure");
                }
            }
        });
    }

    private void getUsernameList(ParseObject gamePlayers) {
        String[] gamePlayerIds = new String[gamePlayers.getJSONArray("users")
                .length()];
        JSONArray userInfo =  gamePlayers.getJSONArray("users");
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

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereContainedIn("objectId",
                Arrays.asList(gamePlayerIds));
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> users, ParseException e) {
                if (e == null) {
                    for (ParseUser user : users) {
                        Log.d("Parse Username",
                                "Retrieved User " + user.getString("username"));
                        addToListView(user.getString("username"),
                                getPlayerAdapter());
                    }
                } else {
                    Log.w("Parse Error", "player username retreival failure");
                }
            }
        });
    }

    private void setItemList(ParseObject game) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameItems");
        query.whereEqualTo("game", game);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject gameItems, ParseException e) {
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
                } else {
                    Log.w("Parse Error", "game items retreival failure");
                }
            }
        });
    }

    private void setupUsernameListView() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        final ListView playerListView = (ListView) findViewById(R.id.listview_currentPlayers);
        playerListView.setAdapter(adapter);
    }

    private void setupItemListView() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        final ListView itemListView = (ListView) findViewById(R.id.listview_gameItems);
        itemListView.setAdapter(adapter);
    }

    private void addToListView(String item, ArrayAdapter<String> adapter) {
        adapter.add(item);
        adapter.notifyDataSetChanged();
    }

    private ArrayAdapter<String> getPlayerAdapter() {
        final ListView playerListView = (ListView) findViewById(R.id.listview_currentPlayers);
        final ArrayAdapter<String> adapter = (ArrayAdapter<String>) playerListView
                .getAdapter();
        return adapter;
    }

    private ArrayAdapter<String> getItemAdapter() {
        final ListView itemListView = (ListView) findViewById(R.id.listview_gameItems);
        final ArrayAdapter<String> adapter = (ArrayAdapter<String>) itemListView
                .getAdapter();
        return adapter;
    }

    private void getGame() {
        Bundle extras = getIntent().getExtras();
        String gameId = extras.getString("gameId");
        Log.d("Game Info", "Game View ID is " + gameId);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Game");
        query.getInBackground(gameId, new GetCallback<ParseObject>() {
            public void done(ParseObject game, ParseException e) {
                if (e == null) {
                    Log.d("Game Info", "Game name is " + game.getString("name"));
                    setGameInfo(game);
                } else {
                    Log.w("error", "game retreival failure");
                }
            }
        });
    }
}

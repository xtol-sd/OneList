package com.example.scavengerhunt;

import java.util.List;

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
        Bundle extras = getIntent().getExtras();
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
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GamePlayer");
        query.whereEqualTo("game", game);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> playerList, ParseException e) {
                if (e == null) {
                    Log.d("User List", "Retrieved " + playerList.size()
                            + " player(s)");
                    getUsernameList(playerList);
                } else {
                    Log.w("Parse Error", "game retreival failure");
                }
            }
        });
    }

    private void getUsernameList(List<ParseObject> playerList) {
        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).getParseObject("user")
                    .fetchIfNeededInBackground(new GetCallback<ParseUser>() {
                        public void done(ParseUser user, ParseException e) {
                            if (e == null) {
                                Log.d("Parse Username", "Retrieved User "
                                        + user.getString("username"));
                                addToListView(user.getString("username"),
                                        getPlayerAdapter());
                            } else {
                                Log.w("Parse Error", "game retreival failure");

                            }
                        }
                    });
        }
    }

    private void setItemList(ParseObject game) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameItem");
        query.whereEqualTo("game", game);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> itemList, ParseException e) {
                if (e == null) {
                    Log.d("Parse Item", "Retrieved " + itemList.size()
                            + " item(s)");
                    for (int i = 0; i < itemList.size(); i++) {
                        addToListView(itemList.get(i).getString("name"),
                                getItemAdapter());
                    }
                } else {
                    Log.w("Parse Error", "game retreival failure");
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

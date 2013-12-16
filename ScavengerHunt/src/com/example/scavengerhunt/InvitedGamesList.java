package com.example.scavengerhunt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class InvitedGamesList extends Activity {

    private final ParseUser currentUser = ParseUser.getCurrentUser();
    List<ParseObject> invitedGames = new ArrayList<ParseObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseAnalytics.trackAppOpened(getIntent());
        setContentView(R.layout.activity_invited_game);
        setInvitedGamesListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.invited_game, menu);
        return true;
    }

    private void setInvitedGamesListView() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        final ListView invitedGamesListview = (ListView) findViewById(R.id.listview_invitedGames);
        invitedGamesListview.setAdapter(adapter);
        invitedGamesListview
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                            final View view, int position, long id) {
                        final ParseObject game = invitedGames.get(position);
                        launchGameView(game.getObjectId());
                    }
                });
        findInvitedGames();

    }

    private void launchGameView(final String gameId) {
        final Intent intent = new Intent(InvitedGamesList.this, GameHub.class);
        intent.putExtra("gameId", gameId);
        Log.d("GameId", "game id is " + gameId);
        startActivity(intent);
    }

    private void addToListView(final ParseObject game,
            final ArrayAdapter<String> adapter) {
        adapter.add(game.getString("name"));
        adapter.notifyDataSetChanged();
    }

    private void findInvitedGames() {
        final ParseQuery<ParseObject> query = ParseQuery
                .getQuery("GamePlayers");
        query.whereEqualTo("users", currentUser);
        query.include("game");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> gamePlayers, ParseException e) {
                if (e == null) {
                    for (final ParseObject gamePlayerObject : gamePlayers) {
                        final ParseObject game = gamePlayerObject
                                .getParseObject("game");
                        Date startDatetime = game.getDate("start_datetime");
                        Date endDatetime = game.getDate("end_datetime");
                        if (new Date().before(endDatetime) && new Date().after(startDatetime) && game.getParseUser("winner") == null) {
                            invitedGames.add(game);
                            addToListView(game, getInvitedGamesAdapter());
                        }
                    }
                } else {
                    Log.w("error", "game retrieval failure");
                }
            }
        });
    }

    private ArrayAdapter<String> getInvitedGamesAdapter() {
        final ListView myGamesListView = (ListView) findViewById(R.id.listview_invitedGames);
        final ArrayAdapter<String> adapter = (ArrayAdapter<String>) myGamesListView
                .getAdapter();
        return adapter;
    }

}

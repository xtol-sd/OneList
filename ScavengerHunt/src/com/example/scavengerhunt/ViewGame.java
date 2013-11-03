package com.example.scavengerhunt;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ViewGame extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_game);
        getGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_game, menu);
        return true;
    }

    public void setGameInfo(ParseObject game) {
        TextView gameName = (TextView) findViewById(R.id.editGameName);
        gameName.setText(game.getString("name"));
    }

    public void getGame() {
        Bundle extras = getIntent().getExtras();
        String gameId = extras.getString("gameId");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("game");
        query.getInBackground(gameId, new GetCallback<ParseObject>() {
            public void done(ParseObject game, ParseException e) {
                if (e == null) {
                    setGameInfo(game);
                } else {
                    Log.w("error", "game retreival failure");
                }
            }
        });
    }
}
